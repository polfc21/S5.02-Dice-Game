package com.itacademy.dicegame.repositories;

import com.itacademy.dicegame.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query(value = "SELECT * FROM players WHERE rate_wins IS NOT NULL ORDER BY rate_wins DESC LIMIT 1", nativeQuery = true)
    Player getBestPlayer();

    @Query(value = "SELECT * FROM players WHERE rate_wins IS NOT NULL ORDER BY rate_wins LIMIT 1", nativeQuery = true)
    Player getWorstPlayer();

}
