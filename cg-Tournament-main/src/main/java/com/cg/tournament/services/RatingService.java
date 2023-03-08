package com.cg.tournament.services;

import com.cg.tournament.DAO.RatingRepository;
import com.cg.tournament.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public Rating addRating(Rating rating){
    	
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Long userId, Long playerId, double newRating) throws Exception {
        Rating optionalRating = ratingRepository.findByUserIdAndPlayerId(userId, playerId);
        if (optionalRating == null) {
            throw new Exception("Rating not found for user " + userId + " and player " + playerId);
        }
        Rating rating = optionalRating;
        rating.setRating(newRating);
        return ratingRepository.save(rating);
    }

    public void deleteRating(Long userId, Long playerId) throws Exception {
        Rating optionalRating = ratingRepository.findByUserIdAndPlayerId(userId, playerId);
        if (optionalRating == null) {
            throw new Exception("Rating not found for user " + userId + " and player " + playerId);
        }
        ratingRepository.delete(optionalRating);
    }
    
    public List<Rating> ratingsByPlayerId(Long playerId){
    	return ratingRepository.ratingsByPlayerId(playerId);
    }
    public List<Rating> getAllRatings(){
    	return ratingRepository.findAll();
    }
}
