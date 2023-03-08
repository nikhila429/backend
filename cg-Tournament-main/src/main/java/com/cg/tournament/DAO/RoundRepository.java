package com.cg.tournament.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.tournament.models.Round;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByTournamentIdOrderByRoundNumber(Long tournamentId);
}
