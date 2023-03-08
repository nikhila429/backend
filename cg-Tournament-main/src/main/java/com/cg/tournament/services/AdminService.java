package com.cg.tournament.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.tournament.DAO.AdminRepository;
import com.cg.tournament.DAO.PlayerRepository;
import com.cg.tournament.DAO.AdminWithUsernameRepository;
import com.cg.tournament.DTOs.AdminWithUsername;
import com.cg.tournament.exceptions.BadRequestException;
import com.cg.tournament.models.Admin;
import com.cg.tournament.models.Player;
import com.cg.tournament.models.User;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    AdminRepository adminRepository;
    
    @Autowired
    PlayerRepository playerRepository;
    
    @Autowired
    AdminWithUsernameRepository adminWithUsernameRepository;
    @Autowired
    TournamentService tournamentService;
    @Autowired
    UserService userService;

    public List<AdminWithUsername> getAdminsByTournamentId(Long tournamentId) {
        return adminWithUsernameRepository.findByTournamentId(tournamentId);
    }

    public Admin getByAdminId(Long adminId) {
        return adminRepository.getById(adminId);
    }

    public boolean isAdminByTournamentId(Long tournamentId, Long adminId) {
        List<Admin> admins = adminRepository.findByTournamentId(tournamentId);
        for (Admin admin : admins) if (admin.getUserId().equals(adminId)) return true;
        return false;
    }

    public boolean isAdminByPairingId(Long pairingId, Long adminId){
        List<Admin> admins = adminRepository.findByPairingId(pairingId);
        System.out.println(admins.size());
        for (Admin admin : admins) if (admin.getUserId().equals(adminId)) return true;
        return false;
    }

    public void deleteByAdminId(Long adminId) {
        adminRepository.deleteById(adminId);
    }

    public AdminWithUsername addAdmin(Long tournamentId, String username) {
        if (! userService.isUserByUsername(username)) throw new BadRequestException("This user does not exist");
        User user = userService.getUserByUsername(username);
        if (tournamentService.isOwner(tournamentId, user.getId())) throw new BadRequestException("Owner cannot be admin");
        if (isAdminByTournamentId(tournamentId, user.getId())) throw new BadRequestException("This user is already an admin");

        Admin admin = new Admin();
        admin.setUserId(user.getId());
        admin.setTournamentId(tournamentId);
        return adminWithUsernameRepository.findByAdminId(adminRepository.save(admin).getId());
    }
    
    public List<Player> getRqstByTournamentId(Long tournamentId){
    	List<Player> players = playerRepository.findByTournamentId(tournamentId);
    	List<Player> unconfirmedPlayers = new ArrayList<>();

    	for (Player player : players) {
    	    if (!player.isConfirmed()) {
    	        unconfirmedPlayers.add(player);
    	    }
    	}
    	return unconfirmedPlayers;
    }
}
