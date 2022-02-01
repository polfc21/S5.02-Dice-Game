package com.itacademy.dicegame.services;

import com.itacademy.dicegame.entities.Game;
import com.itacademy.dicegame.entities.Player;
import com.itacademy.dicegame.repositories.GameRepository;
import com.itacademy.dicegame.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public Player createPlayer(Player player) {
        return this.playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player player) {
        Optional<Player> optionalPlayer = this.playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player updatedPlayer = this.playerRepository.getById(id);
            updatedPlayer.setName(player.getName());
            return this.playerRepository.save(updatedPlayer);
        }
        return null;
    }

    public Game createGame(Long id) {
        Optional<Player> optionalPlayer = this.playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Game game = new Game(optionalPlayer.get(), new GamePlay());
            this.gameRepository.save(game);
            this.updateRateWins(id);
            return game;
        }
        return null;
    }

    private void updateRateWins(Long id) {
        Player player = this.playerRepository.getById(id);
        Double winGames = (double) player.getGames().stream().filter(Game::getResult).count();
        Integer sizeGames = player.getGames().size();
        player.setRateWins(winGames / sizeGames * 100);
        this.playerRepository.save(player);
    }

    public void deleteAllGames(Long id) {
        if (this.playerRepository.existsById(id)) {
            Player player = this.playerRepository.getById(id);
            this.gameRepository.deleteGamesByPlayer(player);
            player.setRateWins(null);
            this.playerRepository.save(player);
        }
    }

    public List<Player> getAllPlayers() {
        return this.playerRepository.findAll();
    }

    public List<Game> getAllGames(Long id) {
        if (this.playerRepository.existsById(id)) {
            return this.gameRepository.getGamesByPlayer(this.playerRepository.getById(id));
        }
        return null;
    }

    public Double getRanking() {
        double totalGames = this.gameRepository.getTotalGames();
        double totalWins = this.gameRepository.getTotalWins();
        return totalGames != 0 ? totalWins / totalGames * 100 : null;
    }

    public Player getBestPlayer() {
        return this.playerRepository.getBestPlayer();
    }

    public Player getWorstPlayer() {
        return this.playerRepository.getWorstPlayer();
    }
}
