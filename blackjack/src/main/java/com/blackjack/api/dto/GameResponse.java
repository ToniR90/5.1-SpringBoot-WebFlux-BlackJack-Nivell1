package com.blackjack.api.dto;

import com.blackjack.api.model.Card;
import com.blackjack.api.model.Game;

import java.util.List;

public record GameResponse(
        String gameId,
        Long playerId,
        String status,
        List<String> playerHand,
        int playerScore,
        List<String> dealerHand,
        int dealerScore

) {

    public static GameResponse from(Game game) {
        return new GameResponse(
                game.getId(),
                game.getPlayerId(),
                game.getGameStatus().name(),
                game.getPlayerCards().stream().map(Card::toString).toList(),
                game.getPlayerFinalScore(),
                game.getDealerCards().stream().map(Card::toString).toList(),
                game.getDealerFinalScore()
        );
    }
}

