package com.blackjack.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameRequest {

    @NotNull(message = "Player ID is required")
    private Long playerId;

}
