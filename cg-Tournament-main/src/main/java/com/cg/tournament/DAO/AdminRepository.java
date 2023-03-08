package com.cg.tournament.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.tournament.models.Admin;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findByTournamentId(Long tournamentId);

    @Query(value = "SELECT ta.id, ta.tournament_id, ta.user_id FROM pairing p " +
            "JOIN round r ON p.round_id = r.id " +
            "JOIN tournament t ON r.tournament_id = t.id " +
            "JOIN tournament_admin ta ON t.id = ta.tournament_id " +
            "WHERE p.id = :pairingId " +
            "ORDER BY ta.id;", nativeQuery = true)
    List<Admin> findByPairingId(Long pairingId);

}