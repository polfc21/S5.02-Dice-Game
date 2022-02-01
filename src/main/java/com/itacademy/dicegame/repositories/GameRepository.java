package com.itacademy.dicegame.repositories;

import com.itacademy.dicegame.entities.Game;
import com.itacademy.dicegame.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> getGamesByPlayer(Player player);

    @Transactional
    void deleteGamesByPlayer(Player player);

    @Query(value = "SELECT COUNT(*) FROM games", nativeQuery = true)
    int getTotalGames();

    @Query(value = "SELECT COUNT(*) FROM games WHERE result = 1", nativeQuery = true)
    int getTotalWins();
}
