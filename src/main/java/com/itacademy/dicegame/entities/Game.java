package com.itacademy.dicegame.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "games")
public class Game {

    public static int WINNER_COMBINATION = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer firstDice;

    private Integer secondDice;

    private Boolean result;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Game(Player player) {
        this.player = player;
        this.setGame();
    }

    private void setGame() {
        this.setFirstDice(this.getRandomDice());
        this.setSecondDice(this.getRandomDice());
        boolean result = this.getFirstDice() + this.getSecondDice() == Game.WINNER_COMBINATION;
        this.setResult(result);
    }

    private int getRandomDice() {
        return new Random().nextInt(6) + 1;
    }

}
