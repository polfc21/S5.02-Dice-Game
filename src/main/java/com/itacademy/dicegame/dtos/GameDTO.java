package com.itacademy.dicegame.dtos;

import com.itacademy.dicegame.entities.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long id;
    private Integer firstDice;
    private Integer secondDice;
    private Boolean result;

    public GameDTO(Game game){
        this.id = game.getId();
        this.firstDice = game.getFirstDice();
        this.secondDice = game.getSecondDice();
        this.result = game.getResult();
    }

}
