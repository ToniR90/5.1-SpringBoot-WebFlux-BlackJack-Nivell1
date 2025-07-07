package com.blackjack.api.dto;

import com.blackjack.api.model.Player;

public record PlayerResponse(
        Long id,
        String name,
        int playedGames,
        int wonGames,
        double victoryRatio
) {
    public static PlayerResponse from(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getName(),
                player.getPlayedGames(),
                player.getWonGames(),
                player.getVictoryRatio()
        );
    }
}