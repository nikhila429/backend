package com.cg.tournament.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.tournament.exceptions.ForbiddenException;
import com.cg.tournament.models.Player;
import com.cg.tournament.services.AdminService;
import com.cg.tournament.services.TournamentService;
import com.cg.tournament.services.PlayerService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    TournamentService tournamentService;
    
    @Autowired
    PlayerService playerService;

    @GetMapping("/api/admins/{tournamentId}")
    public ResponseEntity<?> get(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(adminService.getAdminsByTournamentId(tournamentId), HttpStatus.OK);
    }

    @PostMapping("api/add/admins/{tournamentId}")
    public ResponseEntity<?> add(@PathVariable Long tournamentId, @RequestParam(name = "username") String username, HttpServletRequest request) {
        //only admins or the owner can add admin
//        if (
//            adminService.isAdminByTournamentId(tournamentId, Long.parseLong(request.getHeader("id").toString()))
//            || tournamentService.isOwner(tournamentId, Long.parseLong(request.getHeader("id").toString()))
//        ) {
            return new ResponseEntity<>(adminService.addAdmin(tournamentId, username), HttpStatus.OK);
//        } else {
//            throw new ForbiddenException("Forbidden");
//        }
    }

    @DeleteMapping("/api/delete/admins/{adminId}")
    public ResponseEntity<?> delete(@PathVariable Long adminId, HttpServletRequest request) {
        //only admins or the owner can delete admin
        Long tournamentId = adminService.getByAdminId(adminId).getTournamentId();
//        if (
//            adminService.isAdminByTournamentId(tournamentId, Long.parseLong(request.getAttribute("id").toString()))
//            || tournamentService.isOwner(tournamentId, Long.parseLong(request.getAttribute("id").toString()))
//        ) {
//            adminService.deleteByAdminId(adminId);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            throw new ForbiddenException("Forbidden");
//        }
        if (
                adminService.isAdminByTournamentId(tournamentId, adminId)
                || tournamentService.isOwner(tournamentId, adminId)
            ) {
                adminService.deleteByAdminId(adminId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new ForbiddenException("Forbidden");
            }
    }
    
    @GetMapping("/api/admins/check-rqst/{tournamentId}")
    public ResponseEntity<?> getPlayerRqst(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(adminService.getRqstByTournamentId(tournamentId), HttpStatus.OK);
    }
    @PutMapping("/api/admins/accept-rqst")
    public ResponseEntity<?> accpetPlayerRqst(@RequestBody Player player) {
        return new ResponseEntity<>(playerService.updatePlayer(player), HttpStatus.OK);
    }
    @DeleteMapping("/api/admins/delete-rqst/{id}")
    public ResponseEntity<?> deletePlayerRqst(@PathVariable Long id) {
        return new ResponseEntity<>(playerService.deletePlayer(id), HttpStatus.OK);
    }
    
}
