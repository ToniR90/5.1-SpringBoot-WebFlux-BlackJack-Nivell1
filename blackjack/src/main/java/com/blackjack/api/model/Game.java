package com.blackjack.api.model;

import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private Long playerId;
    private List<Card> playerCards = new ArrayList<>();
    private List<Card> dealerCards = new ArrayList<>();
    private GameStatus gameStatus;
    private int playerFinalScore;
    private int dealerFinalScore;

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
                "Game Status: " + gameStatus + "\n" +
                "Player final score: " + playerFinalScore + "\n" +
                "Dealer final score: " + dealerFinalScore;
    }
}
