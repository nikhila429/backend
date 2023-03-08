package com.cg.tournament.DAO;

import com.cg.tournament.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByUserIdAndPlayerId(Long userId, Long playerId);
    
    
    @Query(value = "SELECT * FROM rating WHERE playerid = :playerId", nativeQuery = true)
    List<Rating> ratingsByPlayerId(@Param("playerId") Long playerId);
}
