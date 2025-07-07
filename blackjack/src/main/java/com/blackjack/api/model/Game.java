package com.blackjack.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "games")
public class Game {

    @org.springframework.data.annotation.Id
    private String id;

    @NotNull(message = "Player ID cannot be null")
    private Long playerId;

    @Builder.Default
    private List<Card> playerCards = new ArrayList<>();

    @Builder.Default
    private List<Card> dealerCards = new ArrayList<>();

    @Builder.Default
    private List<Card> remainingDeck = new ArrayList<>();

    private GameStatus gameStatus;
    private int playerFinalScore;
    private int dealerFinalScore;

    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime playedAt = LocalDateTime.now();

    public enum GameStatus{
        WIN , LOSS , DRAW , IN_PROGRESS
    }

    @Override
    public String toString() {
        return  "Game id: " + id + "\n" +
                "Player Id: " + playerId + "\n" +
                "Player Cards: " + playerCards + "\n" +
                "Dealer Cards: " + dealerCards + "\n" +
                "Game Status: " + (gameStatus != null ? gameStatus.name() : "NOT_SET") + "\n" +
                "Player final score: " + playerFinalScore + "\n" +
                "Dealer final score: " + dealerFinalScore;
    }
}