package com.blackjack.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void startGame() {
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