package com.blackjack.api.model;

import jakarta.persistence.Id;
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

    @Id
    private String id;

    @NotNull(message = "Player ID cannot be null")
    private Long playerId;

    @Builder.Default
    private List<Card> playerCards = new ArrayList<>();

    @Builder.Default
    private List<Card> dealerCards = new ArrayList<>();

    private GameStatus gameStatus;
    private int playerFinalScore;
    private int dealerFinalScore;
    private LocalDateTime playedAt = LocalDateTime.now();

    public enum GameStatus{
        WIN , LOSS , DRAW
    }

    public boolean playerWins(){
        return gameStatus == GameStatus.WIN;
    }

    public boolean isDraw(){
        return gameStatus == GameStatus.DRAW;
    }

    public boolean dealerWins(){
        return gameStatus == GameStatus.LOSS;
    }

    public boolean isDealerBlackjack(){
        return dealerCards.size() == 2 && dealerFinalScore == 21;
    }

    @Override
    public String toString() {
        return "Game id: " + id + "\n" +
                "Player Id: " + playerId + "\n" +
                "Player Cards: " + playerCards + "\n" +
                "Dealer Cards: " + dealerCards + "\n" +
                "Game Status: " + (gameStatus != null ? gameStatus.name() : "NOT_SET") + "\n" +
                "Player final score: " + playerFinalScore + "\n" +
                "Dealer final score: " + dealerFinalScore;
    }
}