package com.cg.tournament.controllers;

import com.cg.tournament.models.Rating;
import com.cg.tournament.services.RatingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/addrating")
    public ResponseEntity<?> addRating(@RequestBody Rating rating) {
    	if(rating.getRating() > 10 || rating.getRating() < 0) {
    		return  new ResponseEntity<>("Rating should be between 0 - 10", HttpStatus.BAD_REQUEST);
    	}
        Rating newRating = ratingService.addRating(rating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/{playerId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long userId, @PathVariable Long playerId, @RequestBody double newRating) throws Exception {
        Rating updatedRating = ratingService.updateRating(userId, playerId, newRating);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{playerId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long userId, @PathVariable Long playerId) throws Exception {
        ratingService.deleteRating(userId, playerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Rating>> getRatingsByPlayerId(@PathVariable Long playerId) {
        List<Rating> ratings = ratingService.ratingsByPlayerId(playerId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/player-average-rating/{playerId}")
    public ResponseEntity<?> getAverageRatingByPlayerId(@PathVariable Long playerId) {
        List<Rating> ratings = ratingService.ratingsByPlayerId(playerId);
        if (ratings.isEmpty()) {
        	double average = 10.0;
        	return new ResponseEntity<>(average, HttpStatus.OK);
//            return new ResponseEntity<>("No ratings found for player with id " + playerId, HttpStatus.NOT_FOUND);
        }
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        double average = sum / ratings.size();
        return new ResponseEntity<>(average, HttpStatus.OK);
    }
    @GetMapping("/getAllRatings")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
    
}
