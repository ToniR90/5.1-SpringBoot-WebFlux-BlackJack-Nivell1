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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
    }

    @Test
    void getAllGames() {
    }

    @Test
    void deleteGame() {
    }
}