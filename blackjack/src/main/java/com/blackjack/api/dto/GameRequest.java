package com.blackjack.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GameRequest {

    @NotNull(message = "Player ID is required")
    private Long playerId;
}
