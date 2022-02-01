package com.itacademy.dicegame.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "player")
    @Singular("game")
    private List<Game> games;

    @Column(name = "rate_wins")
    private Double rateWins;

    @Column(name = "registry_date")
    private LocalDate registryDate;

    public Player(String name) {
        this.name = name;
        this.registryDate = LocalDate.now();
    }

}
