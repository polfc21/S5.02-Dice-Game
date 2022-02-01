package com.itacademy.dicegame.services;

import lombok.Data;

import java.util.Random;

@Data
public class GamePlay {

    public static int WINNER_COMBINATION = 7;

    private Integer firstDice;
    private Integer secondDice;
    private Boolean result;

    public GamePlay() {
        this.firstDice = this.getRandomDice();
        this.secondDice = this.getRandomDice();
        this.result = this.firstDice + this.secondDice == GamePlay.WINNER_COMBINATION;
    }

    private int getRandomDice() {
        return new Random().nextInt(6) + 1;
    }
}
