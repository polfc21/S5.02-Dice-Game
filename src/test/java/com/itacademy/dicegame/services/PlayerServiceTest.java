package com.itacademy.dicegame.services;

import com.itacademy.dicegame.entities.Game;
import com.itacademy.dicegame.entities.Player;
import com.itacademy.dicegame.repositories.GameRepository;
import com.itacademy.dicegame.repositories.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService sut;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameRepository gameRepository;

    private Player player;
    private List<Player> players;
    private Game game;
    private GamePlay gamePlay;
    private List<Game> games;


    @BeforeEach
    public void setUp() {
        this.player = new Player("Pol Farreny Capdevila");
        this.players = List.of(this.player);
        this.gamePlay = new GamePlay();
        this.game = new Game(this.player, this.gamePlay);
        this.games = List.of(this.game);
    }

    @Test
    public void testGivenPlayerWhenCreatePlayerThenReturnPlayer() {
        when(this.playerRepository.save(this.player)).thenReturn(this.player);

        assertThat(this.sut.createPlayer(this.player), is(this.player));
    }

    @Test
    public void testGivenPresentIdPlayerWhenUpdatePlayerThenReturnPlayer() {
        Long presentId = 1L;

        when(this.playerRepository.findById(presentId)).thenReturn(Optional.of(this.player));
        when(this.playerRepository.getById(presentId)).thenReturn(this.player);
        when(this.playerRepository.save(this.player)).thenReturn(this.player);

        assertThat(this.sut.updatePlayer(presentId, this.player), is(this.player));
    }

    @Test
    public void testGivenNotPresentIdPlayerWhenUpdatePlayerThenReturnNull() {
        Long notPresentId = 1L;

        when(this.playerRepository.findById(notPresentId)).thenReturn(Optional.empty());
        verify(this.playerRepository, never()).getById(notPresentId);
        verify(this.playerRepository, never()).save(this.player);

        Assertions.assertNull(this.sut.updatePlayer(notPresentId, null));
    }

    @Test
    public void testGivenNotPresentIdPlayerWhenCreateGameThenReturnNull() {
        Long notPresentId = 1L;

        when(this.playerRepository.findById(notPresentId)).thenReturn(Optional.empty());
        verify(this.gameRepository, never()).save(this.game);

        Assertions.assertNull(this.sut.createGame(notPresentId));
    }

    @Test
    public void testGivenPresentIdPlayerWhenDeleteAllGamesThenDeleteGamesByPlayer() {
        Long presentId = 1L;

        when(this.playerRepository.existsById(presentId)).thenReturn(true);
        when(this.playerRepository.getById(presentId)).thenReturn(this.player);
        this.sut.deleteAllGames(presentId);

        verify(this.gameRepository, times(1)).deleteGamesByPlayer(this.player);
        verify(this.playerRepository, times(1)).save(this.player);
    }

    @Test
    public void testGivenNotPresentIdPlayerWhenDeleteAllGamesThenNeverDeleteGamesByPlayer() {
        Long notPresentId = 1L;

        when(this.playerRepository.existsById(notPresentId)).thenReturn(false);
        this.sut.deleteAllGames(notPresentId);

        verify(this.gameRepository, never()).deleteGamesByPlayer(this.player);
        verify(this.playerRepository, never()).save(this.player);
    }

    @Test
    public void testWhenGetAllPlayersThenReturnPlayers() {
        when(this.playerRepository.findAll()).thenReturn(this.players);

        assertThat(this.sut.getAllPlayers(), is(this.players));
    }

    @Test
    public void testGivenPresentIdPlayerWhenGetAllGamesThenReturnGames() {
        Long presentId = 1L;

        when(this.playerRepository.existsById(presentId)).thenReturn(true);
        when(this.playerRepository.getById(presentId)).thenReturn(this.player);
        when(this.gameRepository.getGamesByPlayer(this.player)).thenReturn(this.games);

        assertThat(this.sut.getAllGames(presentId), is(this.games));
    }

    @Test
    public void testGivenNotPresentIdPlayerWhenGetAllGamesThenReturnNull() {
        Long notPresentId = 1L;

        when(this.playerRepository.existsById(notPresentId)).thenReturn(false);

        Assertions.assertNull(this.sut.getAllGames(notPresentId));
    }

    @Test
    public void testGivenNoGamesWhenGetRankingThenReturnNull() {
        when(this.gameRepository.getTotalGames()).thenReturn(0);

        Assertions.assertNull(this.sut.getRanking());
    }

    @Test
    public void testGivenRegisteredGamesWhenGetRankingThenReturnRankingRate() {
        int totalGames = 1;
        int totalWins = 1;
        double rankingRate = 100.0;

        when(this.gameRepository.getTotalGames()).thenReturn(totalGames);
        when(this.gameRepository.getTotalWins()).thenReturn(totalWins);

        assertThat(this.sut.getRanking(), is(rankingRate));
    }

    @Test
    public void testWhenGetBestPlayerThenReturnPlayer() {
        when(this.playerRepository.getBestPlayer()).thenReturn(this.player);

        assertThat(this.sut.getBestPlayer(), is(this.player));
    }

    @Test
    public void testWhenGetWorstPlayerThenReturnPlayer() {
        when(this.playerRepository.getWorstPlayer()).thenReturn(this.player);

        assertThat(this.sut.getWorstPlayer(), is(this.player));
    }

}
