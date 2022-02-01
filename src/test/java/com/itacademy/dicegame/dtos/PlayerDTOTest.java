package com.itacademy.dicegame.dtos;

import com.itacademy.dicegame.entities.Player;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PlayerDTOTest {

    @Test
    public void testGivenPlayerDTOWithNullNameWhenToPlayerThenPlayerNameIsAnonim() {
        PlayerDTO playerDTO = new PlayerDTO();

        Player player = playerDTO.toPlayer();

        assertThat(player.getName(), is(PlayerDTO.ANONIM));
    }

    @Test
    public void testGivenPlayerDTOWithNotNullNameWhenToPlayerThenPlayerNameIsPlayerDTOName() {
        PlayerDTO playerDTO = new PlayerDTO(null,"Pol",null,null,null);

        Player player = playerDTO.toPlayer();

        assertThat(player.getName(), is(playerDTO.getName()));
    }
}
