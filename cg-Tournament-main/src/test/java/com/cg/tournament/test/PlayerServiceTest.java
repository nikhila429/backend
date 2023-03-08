package com.cg.tournament.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.tournament.models.Player;
import com.cg.tournament.services.PlayerService;
import com.cg.tournament.DAO.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;


    @Test
    void testAddPlayer() {
        // Create a dummy player
        Player player = new Player(1L, 1L, 1L, "Player 1", false, false);

        // Define the behavior of the player repository mock
        when(playerRepository.save(player)).thenReturn(player);

        // Call the player service method
        Player result = playerService.addPlayer(player);

        // Check that the result matches the expected output
        assertEquals(player, result);
    }

    @Test
    void testUpdatePlayer() {
        // Create a dummy player
        Player player = new Player(1L, 1L, 1L, "Player 1", false, false);

        // Define the behavior of the player repository mock
        when(playerRepository.save(player)).thenReturn(player);

        // Call the player service method
        Optional<Player> playerOptional = playerRepository.findById(player.getId());
        
        Player result = playerService.updatePlayer(player);
        if (!playerOptional.isPresent()) {
        	assertEquals(null, result);
        }
        else {
        	// Check that the result matches the expected output
            assertEquals(player, result);
        }

        
    }

    @Test
    void testDeletePlayer() {
        // Define the ID of the player to delete
        Long playerId = 1L;

        // Define the behavior of the player repository mock
        when(playerRepository.existsById(playerId)).thenReturn(true);
        doNothing().when(playerRepository).deleteById(playerId);
        
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        // Call the player service method
        boolean result = playerService.deletePlayer(playerId);

        // Check that the result matches the expected output
        

        if (!playerOptional.isPresent()) {
        	assertFalse(result);
        }else {
        	assertTrue(result);
        }
        
    }
}
