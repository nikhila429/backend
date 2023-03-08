package com.cg.tournament.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.tournament.DAO.RatingRepository;
import com.cg.tournament.models.Rating;
import com.cg.tournament.services.RatingService;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

	@InjectMocks
	RatingService ratingService;

	@Mock
	RatingRepository ratingRepository;

	private Rating rating1;
	private Rating rating2;

	@BeforeEach
	void setUp() {
		rating1 = new Rating(1L, 1L, 8.5);
		rating1.setId(1L);
		rating2 = new Rating(2L, 1L, 6.0);
		rating2.setId(2L);
	}

	@Test
	void testAddRating() {
		Mockito.when(ratingRepository.save(rating1)).thenReturn(rating1);
		Rating result = ratingService.addRating(rating1);
		assertEquals(rating1, result);
	}

	@Test
	void testUpdateRating() throws Exception {
		Mockito.when(ratingRepository.findByUserIdAndPlayerId(1L, 1L)).thenReturn(rating1);
		rating1.setRating(9.0);
		Mockito.when(ratingRepository.save(rating1)).thenReturn(rating1);
		Rating result = ratingService.updateRating(1L, 1L, 9.0);
		assertEquals(rating1, result);
	}

	@Test
	void testUpdateRatingNotFoundException() {
		Mockito.when(ratingRepository.findByUserIdAndPlayerId(1L, 1L)).thenReturn(null);
		assertThrows(Exception.class, () -> ratingService.updateRating(1L, 1L, 9.0));
	}

	@Test
	void testDeleteRating() throws Exception {
		Mockito.when(ratingRepository.findByUserIdAndPlayerId(1L, 1L)).thenReturn(rating1);
		ratingService.deleteRating(1L, 1L);
		Mockito.verify(ratingRepository, Mockito.times(1)).delete(rating1);
	}

	@Test
	void testDeleteRatingNotFoundException() {
		Mockito.when(ratingRepository.findByUserIdAndPlayerId(1L, 1L)).thenReturn(null);
		assertThrows(Exception.class, () -> ratingService.deleteRating(1L, 1L));
	}

	@Test
	void testRatingsByPlayerId() {
		List<Rating> ratings = new ArrayList<>();
		ratings.add(rating1);
		ratings.add(rating2);
		Mockito.when(ratingRepository.ratingsByPlayerId(1L)).thenReturn(ratings);
		List<Rating> result = ratingService.ratingsByPlayerId(1L);
		assertEquals(ratings, result);
	}

}
