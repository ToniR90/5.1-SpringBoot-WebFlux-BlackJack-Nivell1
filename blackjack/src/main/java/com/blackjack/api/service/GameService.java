package com.blackjack.api.service;

import com.blackjack.api.expection.InvalidGameStateException;
import com.blackjack.api.expection.ResourceNotFoundException;
import com.blackjack.api.game.GameLogic;
import com.blackjack.api.model.Card;
import com.blackjack.api.model.Deck;
import com.blackjack.api.model.Game;
import com.blackjack.api.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameLogic gameLogic;
    private final PlayerService playerService;

    public Mono<Game> startNewGame(Long playerId) {
        return playerService.getPlayerById(playerId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Player doesn't exist")))
                .flatMap(player -> {
                    Deck deck = new Deck();
                    deck.shuffle();

                    List<Card> playerHand = List.of(deck.drawCard() , deck.drawCard());
                    List<Card> dealerHand = List.of(deck.drawCard() , deck.drawCard());

                    boolean playerBJ = gameLogic.isBlackjack(playerHand);
                    boolean dealerBJ = gameLogic.isBlackjack(dealerHand);

                    Game.GameStatus status;
                    if (playerBJ && dealerBJ) {
                        status = Game.GameStatus.DRAW;
                    } else if (playerBJ) {
                        status = Game.GameStatus.WIN;
                    } else if (dealerBJ) {
                        status = Game.GameStatus.LOSS;
                    } else {
                        status = Game.GameStatus.IN_PROGRESS;
                    }

                    List<Card> remainingCards = deck.remainingCards();

                    Game game = Game.builder()
                            .playerId(playerId)
                            .playerCards(playerHand)
                            .dealerCards(dealerHand)
                            .playerFinalScore(gameLogic.calculateHandValue(playerHand))
                            .dealerFinalScore(gameLogic.calculateHandValue(dealerHand))
                            .remainingDeck(remainingCards)
                            .gameStatus(status)
                            .build();

                    Mono<Game> savedGame = gameRepository.save(game);

                    if (status != Game.GameStatus.IN_PROGRESS) {
                        boolean playerWon = status == Game.GameStatus.WIN;
                        return playerService.registerGameResult(playerId, playerWon)
                                .then(savedGame);
                    }
                    return savedGame;
                });

    }

    public Mono<Game> playerStands(String gameId){
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Game not found")))
                .flatMap(game -> {
                    Deck deck = new Deck(game.getRemainingDeck());

                    List<Card> dealerHand = new ArrayList<>(game.getDealerCards());
                    while(gameLogic.shouldDealerDraw(dealerHand) && !deck.isEmpty()){
                        dealerHand.add(deck.drawCard());
                    }

                    Game.GameStatus finalStatus = gameLogic.determineGameOutcome(game.getPlayerCards() , dealerHand);

                    game.setDealerCards(dealerHand);
                    game.setDealerFinalScore(gameLogic.calculateHandValue(dealerHand));
                    game.setGameStatus(finalStatus);
                    game.setRemainingDeck(deck.remainingCards());

                    return playerService.registerGameResult(game.getPlayerId(), finalStatus == Game.GameStatus.WIN)
                            .then(gameRepository.save(game));
                });
    }

    public Mono<Game> playerHits(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Game not found")))
                .flatMap(game -> {
                    if (game.getGameStatus() != Game.GameStatus.IN_PROGRESS) {
                        return Mono.error(new InvalidGameStateException("Game is already completed"));
                    }

                    Deck deck = new Deck(game.getRemainingDeck());

                    List<Card> playerHand = new ArrayList<>(game.getPlayerCards());
                    if (deck.isEmpty()) {
                        return Mono.error(new InvalidGameStateException("No more cards in the deck"));
                    }

                    playerHand.add(deck.drawCard());

                    int playerScore = gameLogic.calculateHandValue(playerHand);
                    game.setPlayerCards(playerHand);
                    game.setPlayerFinalScore(playerScore);
                    game.setRemainingDeck(deck.remainingCards());

                    if(gameLogic.isBust(playerHand)) {
                        game.setGameStatus(Game.GameStatus.LOSS);
                        return playerService.registerGameResult(game.getPlayerId(), false)
                                .then(gameRepository.save(game));
                    }
                    game.setGameStatus(Game.GameStatus.IN_PROGRESS);
                    return gameRepository.save(game);
                });
    }

    public Mono<Game> getGameById(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Player not found")));
    }
}