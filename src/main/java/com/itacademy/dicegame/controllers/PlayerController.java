package com.itacademy.dicegame.controllers;

import com.itacademy.dicegame.dtos.GameDTO;
import com.itacademy.dicegame.dtos.PlayerDTO;
import com.itacademy.dicegame.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PlayerController.PLAYERS)
public class PlayerController {

    public static final String PLAYERS = "/players";
    public static final String PLAYER_ID = "/{id}";
    public static final String GAMES = "/games";
    public static final String RANKING = "/ranking";

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        try {
            PlayerDTO playerDTOCreated = new PlayerDTO(this.playerService.createPlayer(playerDTO.toPlayer()));
            return new ResponseEntity<>(playerDTOCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(PlayerController.PLAYER_ID)
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerDTO playerDTO) {
        try {
            PlayerDTO playerDTOUpdated = new PlayerDTO(this.playerService.updatePlayer(id, playerDTO.toPlayer()));
            return new ResponseEntity<>(playerDTOUpdated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(PlayerController.PLAYER_ID + PlayerController.GAMES)
    public ResponseEntity<GameDTO> createGame(@PathVariable Long id) {
        try {
            GameDTO gameDTOCreated = new GameDTO(this.playerService.createGame(id));
            return new ResponseEntity<>(gameDTOCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(PlayerController.PLAYER_ID + PlayerController.GAMES)
    public ResponseEntity<HttpStatus> deleteAllGames(@PathVariable Long id) {
        try {
            this.playerService.deleteAllGames(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> playersDTO = this.playerService.getAllPlayers()
                .stream()
                .map(PlayerDTO::new)
                .collect(Collectors.toList());
        if (playersDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(playersDTO, HttpStatus.OK);
        }
    }

    @GetMapping(PlayerController.PLAYER_ID + PlayerController.GAMES)
    public ResponseEntity<List<GameDTO>> getAllGames(@PathVariable Long id) {
        try {
            List<GameDTO> gamesDTO = this.playerService.getAllGames(id)
                    .stream()
                    .map(GameDTO::new)
                    .collect(Collectors.toList());
            if (gamesDTO.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(gamesDTO,HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(PlayerController.RANKING)
    public ResponseEntity<Double> getRanking() {
        try {
            Double totalRate = this.playerService.getRanking();
            return totalRate == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(totalRate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(PlayerController.RANKING + "/winner")
    public ResponseEntity<PlayerDTO> getBestPlayer() {
        try {
            PlayerDTO bestPlayerDTO = new PlayerDTO(this.playerService.getBestPlayer());
            return new ResponseEntity<>(bestPlayerDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(PlayerController.RANKING + "/loser")
    public ResponseEntity<PlayerDTO> getWorstPlayer() {
        try {
            PlayerDTO worstPlayerDTO = new PlayerDTO(this.playerService.getWorstPlayer());
            return new ResponseEntity<>(worstPlayerDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
