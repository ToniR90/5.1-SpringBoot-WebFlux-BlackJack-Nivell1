package com.blackjack.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PlayerRequest {

    @NotBlank(message = "Name is required")
    private String name;
}