package com.itacademy.dicegame.entities;

import com.itacademy.dicegame.services.GamePlay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer firstDice;
    private Integer secondDice;
    private Boolean result;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @Transient
    private GamePlay gamePlay;

    public Game(Player player, GamePlay gamePlay) {
        this.player = player;
        this.firstDice = gamePlay.getFirstDice();
        this.secondDice = gamePlay.getSecondDice();
        this.result = gamePlay.getResult();
    }

}
