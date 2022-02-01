package com.itacademy.dicegame.dtos;

import com.itacademy.dicegame.entities.Player;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Long id;
    private String name;
    @Singular("game")
    private List<GameDTO> games;
    private Double rateWins;
    private LocalDate registryDate;

    public static String ANONIM = "ANÒNIM";

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        if (player.getGames() != null) {
            this.games = player.getGames().stream().map(GameDTO::new).collect(Collectors.toList());
        }
        this.rateWins = player.getRateWins();
        this.registryDate = player.getRegistryDate();
    }

    public Player toPlayer() {
        if (this.getName() == null) {
            this.setName(PlayerDTO.ANONIM);
        }
        return new Player(this.getName());
    }
}
