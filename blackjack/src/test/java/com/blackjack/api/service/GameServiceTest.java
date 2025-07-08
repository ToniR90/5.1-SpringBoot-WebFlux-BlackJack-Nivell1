package com.blackjack.api.service;

import com.blackjack.api.game.GameLogic;
import com.blackjack.api.repository.mongo.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void playerStands() {
    }

    @Test
    void playerHits() {
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