package com.blackjack.api.controller;

import com.blackjack.api.dto.GameRequest;
import com.blackjack.api.dto.GameResponse;
import com.blackjack.api.game.GameLogic;
import com.blackjack.api.model.Card;
import com.blackjack.api.model.Game;
import com.blackjack.api.model.Player;
import com.blackjack.api.repository.mongo.GameRepository;
import com.blackjack.api.repository.r2dbc.PlayerRepository;
import com.blackjack.api.service.GameService;
import com.blackjack.api.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@WebFluxTest(GameController.class)
@Import(GameService.class)
class GameControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockitoBean
    GameRepository gameRepository;

    @MockitoBean
    PlayerRepository playerRepository;

    @MockitoBean
    GameLogic gameLogic;

    @MockitoBean
    PlayerService playerService;

    @Test
    void startGame() {
        GameRequest request = new GameRequest(1L);
        Game game = Game.builder()
                .playerId(1L)
                .gameStatus(Game.GameStatus.IN_PROGRESS)
                .build();
        game.setId("game123");

        Player mockPlayer = Player.builder()
                .id(1L)
                .name("Toni")
                .build();

        when(playerService.getPlayerById(1L)).thenReturn(Mono.just(mockPlayer));
        when(gameLogic.isBlackjack(anyList())).thenReturn(false);
        when(gameLogic.calculateHandValue(anyList())).thenReturn(13);
        when(gameRepository.save(any())).thenReturn(Mono.just(game));

        webTestClient.post().uri("/games").bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(resp -> assertThat(resp.gameId()).isEqualTo("game123"));
    }

    @Test
    void getGame() {
        Game game = Game.builder()
                .playerId(1L)
                .gameStatus(Game.GameStatus.IN_PROGRESS)
                .build();
        game.setId("123");

        when(gameRepository.findById("123")).thenReturn(Mono.just(game));

        webTestClient.get().uri("/games/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(response -> {
                    assertThat(response.gameId()).isEqualTo("123");
                    assertThat(response.playerId()).isEqualTo(1L);
                });
    }

    @Test
    void getAllGames() {
        Game game = Game.builder()
                .playerId(1L)
                .gameStatus(Game.GameStatus.IN_PROGRESS)
                .playerCards(List.of(new Card(Card.Suit.HEARTS, Card.Rank.TEN)))
                .dealerCards(List.of(new Card(Card.Suit.SPADES, Card.Rank.NINE)))
                .playerFinalScore(10)
                .dealerFinalScore(9)
                .build();
        game.setId("abc");

        Game game2 = Game.builder()
                .playerId(2L)
                .gameStatus(Game.GameStatus.WIN)
                .playerCards(List.of(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)))
                .dealerCards(List.of(new Card(Card.Suit.CLUBS, Card.Rank.FIVE)))
                .playerFinalScore(21)
                .dealerFinalScore(15)
                .build();
        game2.setId("def");

        when(gameRepository.findAll()).thenReturn(Flux.just(game , game2));

        webTestClient.get().uri("/games")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GameResponse.class)
                .hasSize(2)
                .value(list -> {
                    assertThat(list.get(0).gameId()).isEqualTo("abc");
                    assertThat(list.get(1).gameId()).isEqualTo("def");
                });
    }

    @Test
    void playerHits() {
    Game game = Game.builder()
            .playerId(1L)
            .gameStatus(Game.GameStatus.IN_PROGRESS)
            .playerCards(List.of(new Card(Card.Suit.HEARTS, Card.Rank.TEN)))
            .dealerCards(List.of(new Card(Card.Suit.SPADES, Card.Rank.EIGHT)))
            .remainingDeck(List.of(new Card(Card.Suit.HEARTS, Card.Rank.ACE)))
            .playerFinalScore(18)
            .dealerFinalScore(17)
            .build();
    game.setId("h123");

    when(gameRepository.findById("h123")).thenReturn(Mono.just(game));
    when(gameLogic.calculateHandValue(anyList())).thenReturn(18);
    when(gameLogic.isBust(anyList())).thenReturn(false);
    when(gameRepository.save(any())).thenReturn(Mono.just(game));

    webTestClient.put().uri("/games/h123/hit")
        .exchange()
        .expectStatus().isOk()
        .expectBody(GameResponse.class)
        .value(response -> assertThat(response.gameId()).isEqualTo("h123"));
}


@Test
    void playerStands() {
    }

    @Test
    void deleteGame() {
    }
}