package com.cg.tournament.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.tournament.exceptions.UnauthorizedException;
import com.cg.tournament.models.Match;
import com.cg.tournament.models.Round;
import com.cg.tournament.services.AdminService;
import com.cg.tournament.services.MatchService;
import com.cg.tournament.services.TournamentService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MatchesController {

    @Autowired
    MatchService matchService;

    @PostMapping("/api/match/createMatch")
    public ResponseEntity<?> addMatch(@RequestBody Match match) {
        Match savedMatch = matchService.addMatch(match);
        if(savedMatch == null) {
        	return new ResponseEntity<>("round ID Already Exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedMatch, HttpStatus.CREATED);
    }

   
    @DeleteMapping("/api/match/deleteMatch/{id}")
    public ResponseEntity<?> deleteMatch(@PathVariable Long id) {
        boolean deleted = matchService.deleteMatch(id);
        System.out.println("Round ID to delete: " + id);
        if (deleted) {
            return new ResponseEntity<>("Round Found with the given ID and got Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Round Found with the given ID", HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/api/matches/{roundId}")
    public ResponseEntity<?> get(@PathVariable Long roundId) {
        return new ResponseEntity<>(matchService.getPairingsByRoundId(roundId), HttpStatus.OK);
    }

    @GetMapping("/api/match/{matchId}")
    public ResponseEntity<?> getById(@PathVariable Long matchId) {
        return new ResponseEntity<>(matchService.getById(matchId), HttpStatus.OK);
    }

    @PutMapping("/api/protected/match/{matchId}")
    public ResponseEntity<?> putById(@PathVariable Long matchId, @RequestBody Match results, HttpServletRequest request) {
            matchService.updateById(matchId, results);
            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PutMapping("/api/match/update-match/{matchId}")
    public ResponseEntity<?> updateById_mid(@PathVariable Long matchId, @RequestBody Match results, HttpServletRequest request) {
            matchService.updateById_mid(matchId, results);
            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
}
