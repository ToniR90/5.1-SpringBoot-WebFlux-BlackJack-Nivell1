package com.blackjack.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank(message = "The name can't be empty")
    @Column(name = "player_name" , nullable = false)
    private String name;

    @Setter
    @Column(name = "played_games" , nullable = false)
    private int playedGames = 0;

    @Setter
    @Column(name = "won_games" , nullable = false)
    private int wonGames = 0;

    @Setter
    @Column(name = "victory_ratio" , nullable = false)
    private double victoryRatio;

    public void registerGame(boolean won){
        playedGames++;
        if(won) wonGames++;
        victoryRatio = (playedGames == 0) ? 0 : ((double) wonGames / playedGames) * 100;
    }

    @Override
    public String toString() {
        return "Player: " + name + "\n" +
                "Id: " + id + "\n" +
                "Played Games: " + playedGames + "\n" +
                "Won Games: " + wonGames + "\n" +
                "Victory Ratio: " + String.format("%.2f" , victoryRatio) + " %";
    }
}