package com.cg.tournament.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.tournament.services.PlayerService;
import com.cg.tournament.DTOs.PlayerScore;
import com.cg.tournament.models.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/api/players/{tournamentId}")
    public ResponseEntity<?> getPlayerScores(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(playerService.getPlayersByTournamentId(tournamentId), HttpStatus.OK);
    }
    
    @PostMapping("/api/player/trnmt-rqst")
    public ResponseEntity<?> addPlayer(@RequestBody Player player) {
    	
    	if(player.isConfirmed()) {
    		return new ResponseEntity<>("User Cannot Confirm Himself for a Tournament", HttpStatus.BAD_REQUEST);
    	}
        Player savedPlayer = playerService.addPlayer(player);
        if(savedPlayer == null) {
        	return new ResponseEntity<>("Player ID Already Exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }
    
    @PutMapping("/api/player/updatetrnmt-rqst")
    public ResponseEntity<?> updatePlayer(@RequestBody Player player) {
//    	if(player.isConfirmed()) {
//    		return new ResponseEntity<>("User Cannot Confirm Himself for a Tournament", HttpStatus.BAD_REQUEST);
//    	}
        Player updatedPlayer = playerService.updatePlayer(player);
        if (updatedPlayer != null) {
            return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Player Found with the given ID", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/player/deletetrnmt-rqst/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        boolean deleted = playerService.deletePlayer(id);
        System.out.println("player ID to delete: " + id);
        if (deleted) {
            return new ResponseEntity<>("Player Found with the given ID and got Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Player Found with the given ID", HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/api/playernames/{tournamentId}")
    public ResponseEntity<?> getPlayers(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(playerService.getPlayerNamesByTournamentId(tournamentId), HttpStatus.OK);
    }
    @GetMapping("/api/player/winper")
    public ResponseEntity<?> getPlayerWinPerc(@RequestBody Player player) {
        return new ResponseEntity<>(playerService.getMatchWinPercentage(player), HttpStatus.OK);
    }
    
    @GetMapping("/api/players/stats/{tournamentId}")
    public ResponseEntity<?> getPlayersByTournamentId(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(playerService.getPlayersByTournamentId(tournamentId), HttpStatus.OK);
    }
    
    @GetMapping("/api/player/getscores/{playerId}/{tournamentId}")
    public ResponseEntity<?> getPlayerScoreByIds(
            @PathVariable Long playerId,
            @PathVariable Long tournamentId
    ) {
        PlayerScore playerScore = playerService.getPlayerScoreByPlayerIdAndTournamentId(playerId, tournamentId);
        if (playerScore != null) {
            return new ResponseEntity<>(playerScore, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    

}
