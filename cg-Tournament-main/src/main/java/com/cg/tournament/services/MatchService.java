package com.cg.tournament.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.tournament.DAO.MatchesRepository;
import com.cg.tournament.exceptions.BadRequestException;
import com.cg.tournament.exceptions.UnauthorizedException;
import com.cg.tournament.models.Match;
import com.cg.tournament.models.Round;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatchService {

    @Autowired
    MatchesRepository matchesRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    TournamentService tournamentService;

    public List<Match> getPairingsByRoundId(Long roundId) {
        return matchesRepository.findByRoundId(roundId);
    }

    public Match getById(Long pairingId) {
    	try {
            return matchesRepository.findById(pairingId).orElseThrow(() -> new BadRequestException("Invalid pairing Id"));
        }
        catch (Exception e) {
            throw new BadRequestException("Invalid pairing Id");
        }

    }
    
    public void updateById_mid(Long pairingId, Match results) {
    	Match match = matchesRepository.findById(pairingId).get();
        match.setMatchResultFirstPlayer(results.getMatchResultFirstPlayer());
        match.setMatchResultSecondPlayer(results.getMatchResultSecondPlayer());
        match.setGameWinsFirstPlayer(results.getGameWinsFirstPlayer());
        match.setGameWinsSecondPlayer(results.getGameWinsSecondPlayer());
        match.setGameDrawsFirstPlayer(results.getGameDrawsFirstPlayer());
        match.setGameDrawsSecondPlayer(results.getGameDrawsSecondPlayer());
        match.setGameLossesFirstPlayer(results.getGameLossesFirstPlayer());
        match.setGameLossesSecondPlayer(results.getGameLossesSecondPlayer());
        match.setFirstCustomFirstPlayer(results.getFirstCustomFirstPlayer());
        match.setFirstCustomSecondPlayer(results.getFirstCustomSecondPlayer());
        match.setSecondCustomFirstPlayer(results.getSecondCustomFirstPlayer());
        match.setSecondCustomSecondPlayer(results.getSecondCustomSecondPlayer());
        matchesRepository.save(match);
    }

    public void updateById(Long pairingId, Match results) {
//        System.out.println(pairingId +" "+ userId);
//        if (adminService.isAdminByPairingId(pairingId, userId)
//                || tournamentService.isOwnerByPairingId(pairingId, userId)) {
//            updateResults(pairingId, results);
//        } else {
            Optional<Match> match = matchesRepository.findById(pairingId);
            if (match.isPresent() && match.get().getMatchResultFirstPlayer().isBlank()) {
                updateResults(pairingId, results);
            }
            else throw new UnauthorizedException("Results already submitted");
//        }
    }

    private void updateResults(Long pairingId, Match results) {
        if (! (results.getMatchResultFirstPlayer().equals("WIN") ||
                results.getMatchResultFirstPlayer().equals("LOSS") ||
                results.getMatchResultFirstPlayer().equals("DRAW"))
        ) throw new BadRequestException("Bad Request");
        if (! (results.getMatchResultSecondPlayer().equals("WIN") ||
                results.getMatchResultSecondPlayer().equals("LOSS") ||
                results.getMatchResultSecondPlayer().equals("DRAW"))
        ) throw new BadRequestException("Bad Request");

        Match match = matchesRepository.findById(pairingId).get();
        match.setMatchResultFirstPlayer(results.getMatchResultFirstPlayer());
        match.setMatchResultSecondPlayer(results.getMatchResultSecondPlayer());
        match.setGameWinsFirstPlayer(results.getGameWinsFirstPlayer());
        match.setGameWinsSecondPlayer(results.getGameWinsSecondPlayer());
        match.setGameDrawsFirstPlayer(results.getGameDrawsFirstPlayer());
        match.setGameDrawsSecondPlayer(results.getGameDrawsSecondPlayer());
        match.setGameLossesFirstPlayer(results.getGameLossesFirstPlayer());
        match.setGameLossesSecondPlayer(results.getGameLossesSecondPlayer());
        match.setFirstCustomFirstPlayer(results.getFirstCustomFirstPlayer());
        match.setFirstCustomSecondPlayer(results.getFirstCustomSecondPlayer());
        match.setSecondCustomFirstPlayer(results.getSecondCustomFirstPlayer());
        match.setSecondCustomSecondPlayer(results.getSecondCustomSecondPlayer());
        matchesRepository.save(match);
    }
    
    public Match addMatch(Match match) {
        // TODO: Add user ID to the player object before saving
    	if(match.getId() != null) {
    		Optional<Match> matchOptional = matchesRepository.findById(match.getId());
        	System.out.println(match.getId());
            if (matchOptional != null) {
                return null;
            }
    	}
    	
        return matchesRepository.save(match);
    }
    


   public boolean deleteMatch(Long id) {
       Optional<Match> matchOptional = matchesRepository.findById(id);

       if (!matchOptional.isPresent()) {
           return false;
       }
       
       matchesRepository.deleteById(id);
       return true;
   }
    
}
