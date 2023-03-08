package com.cg.tournament.test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.tournament.controllers.RatingController;
import com.cg.tournament.models.Rating;
import com.cg.tournament.services.RatingService;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Add a new rating")
    void testAddRating() {
        Rating newRating = new Rating(1L, 2L, 9.0);
        Rating savedRating = new Rating(1L, 2L, 9.0);
        Rating new_saved_Rating = ratingService.addRating(savedRating);
        ResponseEntity<Rating> responseEntity = new ResponseEntity<>(new_saved_Rating, HttpStatus.CREATED);
        assertEquals(responseEntity, ratingController.addRating(newRating));
    }

    @Test
    @DisplayName("Add a new rating with invalid value")
    void testAddRatingInvalidValue() {
        Rating newRating = new Rating(1L, 2L, 11.0);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Rating should be between 0 - 10", HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity, ratingController.addRating(newRating));
    }

    @Test
    @DisplayName("Update an existing rating")
    void testUpdateRating() throws Exception {
        double newRating = 8.0;
        Rating existingRating = new Rating(1L, 2L, 7.0);
        Rating updatedRating = new Rating(1L, 2L, 8.0);
        ResponseEntity<Rating> responseEntity = new ResponseEntity<>(updatedRating, HttpStatus.OK);
        Mockito.when(ratingService.updateRating(1L, 2L, newRating)).thenReturn(updatedRating);
        assertEquals(responseEntity, ratingController.updateRating(1L, 2L, newRating));
    }

    @Test
    @DisplayName("Update a non-existing rating")
    void testUpdateRatingNotFound() throws Exception {
        double newRating = 8.0;
        Mockito.when(ratingService.updateRating(1L, 2L, newRating)).thenThrow(new Exception("Rating not found for user 1 and player 2"));
        assertThrows(Exception.class, () -> ratingController.updateRating(1L, 2L, newRating));
    }

    @Test
    @DisplayName("Delete an existing rating")
    void testDeleteRating() throws Exception {
        Rating existingRating = new Rating(1L, 2L, 7.0);
        Mockito.doNothing().when(ratingService).deleteRating(1L, 2L);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        assertEquals(responseEntity, ratingController.deleteRating(1L, 2L));
    }

    @Test
    @DisplayName("Delete a non-existing rating")
    void testDeleteRatingNotFound() throws Exception {
        Mockito.doThrow(new Exception("Rating not found for user 1 and player 2")).when(ratingService).deleteRating(1L, 2L);
        assertThrows(Exception.class, () -> ratingController.deleteRating(1L, 2L));
    }

    @Test
    @DisplayName("Get all ratings")
    void testGetAllRatings() {
    	Rating rating1 = new Rating(1L, 2L, 7.0);
    	Rating rating2 = new Rating(3L, 4L, 8.0);
    	List<Rating> ratingList = new ArrayList<>(Arrays.asList(rating1, rating2));
    	Mockito.when(ratingService.getAllRatings()).thenReturn(ratingList);
    	ResponseEntity<List<Rating>> responseEntity = new ResponseEntity<>(ratingList, HttpStatus.OK);
    	assertEquals(responseEntity, ratingController.getAllRatings());
    	}
}
