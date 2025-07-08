package com.blackjack.api.service;

import com.blackjack.api.game.GameLogic;
import com.blackjack.api.model.Card;
import com.blackjack.api.model.Game;
import com.blackjack.api.model.Player;
import com.blackjack.api.repository.mongo.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameLogic gameLogic;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;


    @Test
    void startNewGame() {
        Long playerId = 1L;
        Game mockGame = Game.builder()
                .playerId(playerId)
                .gameStatus(Game.GameStatus.IN_PROGRESS)
                .build();

        when(playerService.getPlayerById(playerId)).thenReturn(Mono.just(new Player()));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame));
        when(gameLogic.isBlackjack(anyList())).thenReturn(false);
        when(gameLogic.calculateHandValue(anyList())).thenReturn(15);

        StepVerifier.create(gameService.startNewGame(playerId))
                .expectNext(mockGame)
                .verifyComplete();
    }


    @Test
    void playerStands() {
        String gameId = "gameStand123";
        Game game = Game.builder()
                .playerId(1L)
                .dealerCards(new ArrayList<>())
                .playerCards(List.of(new Card(Card.Suit.HEARTS, Card.Rank.JACK)))
                .remainingDeck(List.of(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN)))
                .build();

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(game));
        when(gameLogic.shouldDealerDraw(anyList())).thenReturn(true, false);
        when(gameLogic.calculateHandValue(anyList())).thenReturn(17);
        when(gameLogic.determineGameOutcome(anyList(), anyList())).thenReturn(Game.GameStatus.WIN);
        when(playerService.registerGameResult(anyLong(), eq(true))).thenReturn(Mono.empty());
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.playerStands(gameId))
                .expectNext(game)
                .verifyComplete();
    }


    @Test
    void playerHits() {
        String gameId = "game123";
        Game existingGame = Game.builder()
                .playerCards(new ArrayList<>())
                .remainingDeck(List.of(new Card(Card.Suit.HEARTS, Card.Rank.TEN)))
                .gameStatus(Game.GameStatus.IN_PROGRESS)
                .build();

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(existingGame));
        when(gameLogic.calculateHandValue(anyList())).thenReturn(15);
        when(gameLogic.isBust(anyList())).thenReturn(false);
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(existingGame));

        StepVerifier.create(gameService.playerHits(gameId))
                .expectNext(existingGame)
                .verifyComplete();
    }


    @Test
    void getGameById() {
        String gameId = "abc";
        Game game = new Game();

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.getGameById(gameId))
                .expectNext(game)
                .verifyComplete();
    }

    @Test
    void getAllGames() {
        String gameId;
        Game game1 = Game.builder()
                .playerId(1L)
                .gameStatus(Game.GameStatus.WIN)
                .build();

        Game game2 = Game.builder()
                .playerId(2L)
                .gameStatus(Game.GameStatus.LOSS)
                .build();
        game1.setId("game1");
        game2.setId("game2");

        when(gameRepository.findAll()).thenReturn(Flux.just(game1, game2));

        StepVerifier.create(gameService.getAllGames())
                .expectNext(game1)
                .expectNext(game2)
                .verifyComplete();
    }

    @Test
    void deleteGame() {
        String gameId = "abc";
        when(gameRepository.deleteById(gameId)).thenReturn(Mono.empty());

        StepVerifier.create(gameService.deleteGame(gameId))
                .verifyComplete();
    }
}