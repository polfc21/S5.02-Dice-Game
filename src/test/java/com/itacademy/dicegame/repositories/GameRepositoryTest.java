package com.itacademy.dicegame.repositories;

import com.itacademy.dicegame.entities.Game;
import com.itacademy.dicegame.entities.Player;
import com.itacademy.dicegame.services.GamePlay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Player player;
    private Game game;

    @BeforeEach
    public void setUp() {
        this.player = new Player("Pol");
        this.game = new Game(this.player, new GamePlay());
    }

    @Test
    public void testGivenPlayerWithGameWhenGetGamesByPlayerThenReturnGame() {
        this.testEntityManager.persist(this.player);
        this.testEntityManager.persist(this.game);

        List<Game> games = this.gameRepository.getGamesByPlayer(this.player);

        assertThat(games, is(List.of(this.game)));
    }

    @Test
    public void testGivenPlayerWithoutGameWhenGetGamesByPlayerThenReturnEmpty() {
        this.testEntityManager.persist(this.player);

        List<Game> games = this.gameRepository.getGamesByPlayer(this.player);

        assertThat(games, is(List.of()));
    }

    @Test
    public void testGivenPlayerWithPicturesWhenDeleteGamesByPlayerThenIsEmpty() {
        this.testEntityManager.persist(this.player);
        this.testEntityManager.persist(this.game);

        this.gameRepository.deleteGamesByPlayer(this.player);

        assertThat(this.gameRepository.getGamesByPlayer(this.player), is(List.of()));
    }

    @Test
    public void testGivenOneGameSavedWhenGetTotalGamesThenReturnOne() {
        this.testEntityManager.persist(this.player);
        this.testEntityManager.persist(this.game);

        assertThat(this.gameRepository.getTotalGames(), is(1));
    }

    @Test
    public void testGivenOneGameSavedWithWinWhenGetTotalWinsThenReturnOne() {
        this.testEntityManager.persist(this.player);
        this.game.setResult(true);
        this.testEntityManager.persist(this.game);

        assertThat(this.gameRepository.getTotalWins(), is(1));
    }
}
