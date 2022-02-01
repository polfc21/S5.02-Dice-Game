package com.itacademy.dicegame.controllers;

import com.itacademy.dicegame.dtos.GameDTO;
import com.itacademy.dicegame.dtos.PlayerDTO;
import com.itacademy.dicegame.entities.Game;
import com.itacademy.dicegame.entities.Player;
import com.itacademy.dicegame.services.GamePlay;
import com.itacademy.dicegame.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @MockBean
    private PlayerService playerService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<PlayerDTO>> jsonPlayerDTOList;
    @Autowired
    private JacksonTester<PlayerDTO> jsonPlayerDTO;
    @Autowired
    private JacksonTester<List<GameDTO>> jsonGameDTOList;
    @Autowired
    private JacksonTester<GameDTO> jsonGameDTO;

    private Player player;
    private List<Player> players;
    private PlayerDTO playerDTO;
    private List<PlayerDTO> playersDTO;
    private Game game;
    private List<Game> games;
    private GameDTO gameDTO;
    private List<GameDTO> gamesDTO;

    @BeforeEach
    public void setUp() {
        this.player = new Player("Pol Farreny Capdevila");
        this.players = List.of(this.player);
        this.playerDTO = new PlayerDTO(this.player);
        this.playersDTO = List.of(this.playerDTO);
        this.game = new Game(this.player, new GamePlay());
        this.games = List.of(this.game);
        this.gameDTO = new GameDTO(this.game);
        this.gamesDTO = List.of(this.gameDTO);
    }

    @Test
    public void testGivenCorrectPlayerDTOWhenCreatePlayerThenReturnCreated() throws Exception {
        given(this.playerService.createPlayer(this.player)).willReturn(this.player);

        MockHttpServletResponse response = mvc.perform(
                post(PlayerController.PLAYERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonPlayerDTO.write(this.playerDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonPlayerDTO.write(this.playerDTO).getJson()));
    }

    @Test
    public void testGivenCorrectPlayerDTOWhenUpdatePlayerThenReturnOk() throws Exception {
        Long presentId = 1L;
        given(this.playerService.updatePlayer(presentId, this.player)).willReturn(this.player);

        MockHttpServletResponse response = mvc.perform(
                put(PlayerController.PLAYERS + "/" + presentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonPlayerDTO.write(this.playerDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonPlayerDTO.write(this.playerDTO).getJson()));
    }

    @Test
    public void testGivenNotExistentIdWhenUpdatePlayerThenReturnBadRequest() throws Exception {
        Long notPresentId = 1L;
        given(this.playerService.updatePlayer(notPresentId, null)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                put(PlayerController.PLAYERS + "/" + notPresentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonPlayerDTO.write(this.playerDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenIncorrectRequestWhenUpdatePlayerThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                put(PlayerController.PLAYERS + "/" + 1)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenExistentIdPlayerWhenCreateGameThenReturnCreated() throws Exception {
        Long presentId = 1L;
        given(this.playerService.createGame(presentId)).willReturn(this.game);

        MockHttpServletResponse response = mvc.perform(
                post(PlayerController.PLAYERS + "/" + presentId + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonGameDTO.write(this.gameDTO).getJson()));
    }

    @Test
    public void testGivenNotExistentIdPlayerWhenCreateGameThenReturnBadRequest() throws Exception {
        Long notPresentId = 1L;
        given(this.playerService.createGame(notPresentId)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                post(PlayerController.PLAYERS + "/" + notPresentId + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenIncorrectRequestWhenCreateGameThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post(PlayerController.PLAYERS + "/" + "BAD REQUEST" + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenExistentIdWhenDeleteAllGamesThenReturnNoContent() throws Exception {
        long presentId = 1L;

        MockHttpServletResponse response = mvc.perform(
                delete(PlayerController.PLAYERS + "/" + presentId + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenNonSavedPlayersWhenGetAllPlayersThenReturnNoContent() throws Exception {
        given(this.playerService.getAllPlayers()).willReturn(List.of());

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenSavedPlayersWhenGetAllPlayersThenReturnOk() throws Exception {
        given(this.playerService.getAllPlayers()).willReturn(this.players);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonPlayerDTOList.write(this.playersDTO).getJson()));
    }

    @Test
    public void testGivenNonSavedGamesAndPresentIdPlayerWhenGetAllGamesThenReturnNoContent() throws Exception {
        Long presentId = 1L;
        given(this.playerService.getAllGames(presentId)).willReturn(List.of());

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + "/" + presentId + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenNotPresentIdPlayerWhenGetAllGamesThenReturnBadRequest() throws Exception {
        Long notPresentId = 1L;
        given(this.playerService.getAllGames(notPresentId)).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + "/" + notPresentId + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenSavedGamesAndPresentIdPlayerWhenGetAllGamesThenReturnOk() throws Exception {
        Long presentId = 1L;
        given(this.playerService.getAllGames(presentId)).willReturn(this.games);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + "/" + presentId + PlayerController.GAMES)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonGameDTOList.write(this.gamesDTO).getJson()));
    }

    @Test
    public void testGivenSavedGamesWhenGetRankingThenReturnOk() throws Exception {
        Double totalRate = 50.0;
        given(this.playerService.getRanking()).willReturn(totalRate);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + PlayerController.RANKING)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(totalRate.toString()));
    }

    @Test
    public void testGivenNonSavedGamesWhenGetRankingThenReturnNoContent() throws Exception {
        given(this.playerService.getRanking()).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + PlayerController.RANKING)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenSavedGamesWhenGetBestPlayerThenReturnOk() throws Exception {
        given(this.playerService.getBestPlayer()).willReturn(this.player);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + PlayerController.RANKING + "/winner")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonPlayerDTO.write(this.playerDTO).getJson()));
    }

    @Test
    public void testGivenNonSavedGamesWhenGetBestPlayerThenReturnBadRequest() throws Exception {
        given(this.playerService.getBestPlayer()).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + PlayerController.RANKING + "/winner")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

    @Test
    public void testGivenSavedGamesWhenGetWorstPlayerThenReturnOk() throws Exception {
        given(this.playerService.getWorstPlayer()).willReturn(this.player);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + PlayerController.RANKING + "/loser")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonPlayerDTO.write(this.playerDTO).getJson()));
    }

    @Test
    public void testGivenNonSavedGamesWhenGetWorstPlayerThenReturnBadRequest() throws Exception {
        given(this.playerService.getWorstPlayer()).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
                get(PlayerController.PLAYERS + PlayerController.RANKING + "/loser")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(response.getContentAsString(), is(""));
    }

}
