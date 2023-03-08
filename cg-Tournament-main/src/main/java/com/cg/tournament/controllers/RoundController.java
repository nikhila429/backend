package com.cg.tournament.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.tournament.models.Player;
import com.cg.tournament.models.Round;
import com.cg.tournament.services.RoundService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RoundController {

    @Autowired
    RoundService roundService;

    @PostMapping("/api/round/createRound")
    public ResponseEntity<?> addRound(@RequestBody Round round) {
        Round savedRound = roundService.addRound(round);
        if(savedRound == null) {
        	return new ResponseEntity<>("round ID Already Exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedRound, HttpStatus.CREATED);
    }

    
    @PutMapping("/api/round/updateRound")
    public ResponseEntity<?> updateRound(@RequestBody Round round) {
        Round savedRound = roundService.updateRound(round);
        if(savedRound == null) {
        	return new ResponseEntity<>("Round doesn't Exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedRound, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/api/round/deleteRound/{id}")
    public ResponseEntity<?> deleteRound(@PathVariable Long id) {
        boolean deleted = roundService.deleteRound(id);
        System.out.println("Round ID to delete: " + id);
        if (deleted) {
            return new ResponseEntity<>("Round Found with the given ID and got Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Round Found with the given ID", HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    @GetMapping("/api/rounds/{tournamentId}")
    public ResponseEntity<?> get(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(roundService.getRoundsByTournamentId(tournamentId), HttpStatus.OK);
    }
}
