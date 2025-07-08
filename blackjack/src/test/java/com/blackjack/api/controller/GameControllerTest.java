package com.blackjack.api.controller;

import com.blackjack.api.dto.GameRequest;
import com.blackjack.api.dto.GameResponse;
import com.blackjack.api.game.GameLogic;
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
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        when(playerService.getPlayerById(1L)).thenReturn(Mono.just(mockPlayer)); // simulat
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
    }

    @Test
    void getAllGames() {
    }

    @Test
    void playerHits() {
    }

    @Test
    void playerStands() {
    }

    @Test
    void deleteGame() {
    }
}