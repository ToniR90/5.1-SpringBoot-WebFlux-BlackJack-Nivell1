package com.blackjack.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table("players")
public class Player {

    @Id
    private Long id; // R2DBC no genera automàticament si uses Long; pots passar null i deixar que el SGBD l’autogeneri si tens `AUTO_INCREMENT`

    @NotBlank(message = "The name can't be empty")
    private String name;

    private int playedGames;
    private int wonGames;
    private double victoryRatio;

    public void registerGame(boolean won) {
        playedGames++;
        if (won) wonGames++;
        victoryRatio = (playedGames == 0) ? 0 : ((double) wonGames / playedGames) * 100;
    }

    @Override
    public String toString() {
        return "Player: " + name + "\n" +
                "Id: " + id + "\n" +
                "Played Games: " + playedGames + "\n" +
                "Won Games: " + wonGames + "\n" +
                "Victory Ratio: " + String.format("%.2f", victoryRatio) + " %";
    }
}