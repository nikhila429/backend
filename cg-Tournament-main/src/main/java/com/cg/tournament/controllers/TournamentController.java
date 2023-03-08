package com.cg.tournament.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.tournament.exceptions.UnauthorizedException;
import com.cg.tournament.models.Tournament;
import com.cg.tournament.services.TournamentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @PostMapping("/api/add/tournament")
    public ResponseEntity<?> createTournament (@RequestBody Tournament config, HttpServletRequest request) throws UnauthorizedException {
    	System.out.println(request.getHeader("username"));
        if (request.getHeader("username") == null)
            throw new UnauthorizedException("You must login to submit a tournament");
        
        return new ResponseEntity<Object>(tournamentService.createTournament(config), HttpStatus.OK);
    }

    
    @PutMapping("/api/update/tournament")
    public ResponseEntity<?> updateTournament (@RequestBody Tournament config, HttpServletRequest request) throws UnauthorizedException {
        if (request.getHeader("username") == null)
            throw new UnauthorizedException("You must login to submit a tournament");
        return tournamentService.updateTournament(config);
    }
    
    @GetMapping("/api/tournaments")
    public ResponseEntity<?> getTournaments (@PathVariable(required = false) Long userId) {
        return tournamentService.getTournaments(userId);
    }
    
    @GetMapping("/api/tournaments/{userId}")
    public ResponseEntity<?> getTournamentsbyUserId (@PathVariable Long userId) {
        return tournamentService.getTournamentsbyUserId(userId);
    }

    @GetMapping("/api/tournament/{id}")
    public ResponseEntity<?> getTournamentById (@PathVariable Long id) {
        return new ResponseEntity<Object>(tournamentService.getTournamentById(id), HttpStatus.OK);
    }
}