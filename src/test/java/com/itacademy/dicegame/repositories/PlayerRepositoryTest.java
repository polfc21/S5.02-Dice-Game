package com.itacademy.dicegame.repositories;

import com.itacademy.dicegame.entities.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Player bestPlayer;
    private Player worstPlayer;

    @BeforeEach
    public void setUp() {
        this.bestPlayer = new Player("Pol");
        this.bestPlayer.setRateWins(100.0);

        this.worstPlayer = new Player("Andrea");
        this.worstPlayer.setRateWins(0.0);
    }

    @Test
    public void testGivenTwoPlayersWhenGetBestPlayerThenReturnBestPlayer() {
        this.testEntityManager.persist(this.bestPlayer);
        this.testEntityManager.persist(this.worstPlayer);

        Player bestPlayer = this.playerRepository.getBestPlayer();

        assertThat(bestPlayer, is(this.bestPlayer));
    }

    @Test
    public void testGivenNoPlayersWhenGetBestPlayerThenReturnNull() {
        Player bestPlayer = this.playerRepository.getBestPlayer();

        Assertions.assertNull(bestPlayer);
    }

    @Test
    public void testGivenTwoPlayersWhenGetWorstPlayerThenReturnWorstPlayer() {
        this.testEntityManager.persist(this.bestPlayer);
        this.testEntityManager.persist(this.worstPlayer);

        Player worstPlayer = this.playerRepository.getWorstPlayer();

        assertThat(worstPlayer, is(this.worstPlayer));
    }

    @Test
    public void testGivenNoPlayersWhenGetWorstPlayerThenReturnNull() {
        Player worstPlayer = this.playerRepository.getWorstPlayer();

        Assertions.assertNull(worstPlayer);
    }

}
