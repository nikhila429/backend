package com.cg.tournament.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.tournament.DAO.*;
import com.cg.tournament.DTOs.TournamentInFull;
import com.cg.tournament.models.*;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class TournamentService {
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    TournamentAtAGlanceRepository tournamentAtAGlanceRepository;
    @Autowired
    AdminWithUsernameRepository adminWithUsernameRepository;
    @Autowired
    PlayerRepository playerRepository;

    public Tournament createTournament (Tournament config) {
    	config.setRegistrationOpen(true);
    	config.setPlayerRegistrationOn(true);
    	config.setCompetitionType("1 VS 1");
        Tournament tournament = tournamentRepository.save(config);
        return tournament;
    }
    public ResponseEntity<?> updateTournament(Tournament updatedConfig) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(updatedConfig.getId());
        if (optionalTournament.isPresent()) {
            // Update any other fields you need to here
            Tournament savedTournament = tournamentRepository.save(updatedConfig);
            return new ResponseEntity<Object>(savedTournament, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Tournament not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getTournaments (Long userId) {
        return new ResponseEntity<Object>(tournamentAtAGlanceRepository.getTournamentsAtAGlance(), HttpStatus.OK);
    }
    
    
    public ResponseEntity<?> getTournamentsbyUserId (Long userId) {
            ResponseEntity<Object> o = new ResponseEntity<Object>(tournamentAtAGlanceRepository.getTournamentsAtAGlancewithOwnerId(userId), HttpStatus.OK);
            System.out.println(o.getBody());
            return o;
    }
    

    
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        return tournaments;
    }

    

    public TournamentInFull getTournamentById (Long id) {
        TournamentInFull tournamentInFull = new TournamentInFull();
        tournamentInFull.setTournament(tournamentAtAGlanceRepository.findByTournamentId(id));
        tournamentInFull.setAdmins(adminWithUsernameRepository.findByTournamentId(id));
        tournamentInFull.setPlayers(playerRepository.findByTournamentId(id));
        return tournamentInFull;
    }

    public boolean isOwner (Long tournamentId, Long ownerId) {
        return tournamentRepository.getById(tournamentId).getOwnerUserId().equals(ownerId);
    }

    public boolean isOwnerByPairingId(Long pairingId, Long ownerId) {
        return isOwner(tournamentRepository.getIdByPairingId(pairingId), ownerId);
    }
}
