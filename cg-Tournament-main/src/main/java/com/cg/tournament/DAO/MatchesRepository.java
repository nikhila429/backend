package com.cg.tournament.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.tournament.models.Match;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchesRepository extends JpaRepository<Match, Long> {
    List<Match> findByRoundId(@Param("roundId") Long roundId);

    @Query(value = "SELECT * FROM pairing " +
            "WHERE first_player_id = :playerId OR second_player_id = :playerId " +
            "ORDER BY round_id;", nativeQuery = true)
    List<Match> findByPlayerId(@Param("playerId") Long playerId);
    
    @Query(value = "SELECT * FROM pairing where id = :pairingId",  nativeQuery = true)
    Optional<Match> findById(@Param("pairingId") Long pairingId);
}