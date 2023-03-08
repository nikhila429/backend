package com.cg.tournament.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.tournament.DAO.RoundRepository;
import com.cg.tournament.models.Player;
import com.cg.tournament.models.Round;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoundService {

    @Autowired
    RoundRepository roundRepository;

    public List<Round> getRoundsByTournamentId(Long tournamentId) {
        return roundRepository.findByTournamentIdOrderByRoundNumber(tournamentId);
    }
    
    public Round addRound(Round round) {
        // TODO: Add user ID to the player object before saving
    	if(round.getId() != null) {
    		Optional<Round> roundOptional = roundRepository.findById(round.getId());
        	System.out.println(round.getId());
            if (roundOptional.isPresent()) {
                return null;
            }
    	}
        return roundRepository.save(round);
    }
    
   public Round updateRound(Round round) {
       Optional<Round> roundOptional = roundRepository.findById(round.getId());

       if (!roundOptional.isPresent()) {
           return null;
       }

       return roundRepository.save(round);
   }

   public boolean deleteRound(Long id) {
       Optional<Round> roundOptional = roundRepository.findById(id);

       if (!roundOptional.isPresent()) {
           return false;
       }
       
       roundRepository.deleteById(id);
       return true;
   }
   
    
}
