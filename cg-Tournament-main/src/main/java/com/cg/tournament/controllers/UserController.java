package com.cg.tournament.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.tournament.models.User;
import com.cg.tournament.services.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/user/login")
    public ResponseEntity<?> validateUser(@RequestBody User user) {
        return userService.validateUser(user);
    }

    @PostMapping("/api/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/api/protected/user/me")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        return userService.validateToken(authHeader);
    }

}
