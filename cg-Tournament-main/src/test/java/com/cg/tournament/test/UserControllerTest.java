package com.cg.tournament.test;

import com.cg.tournament.controllers.UserController;
import com.cg.tournament.models.User;
import com.cg.tournament.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "testuser", "Test User", "testuser@example.com", "password", true);
    }

    @Test
    void validateUser_ValidUser_ReturnsOK() {
        lenient().when(userService.validateUser(testUser)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = userController.validateUser(testUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void registerUser_ValidUser_ReturnsOK() {
        lenient().when(userService.registerUser(testUser)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = userController.registerUser(testUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void validateToken_ValidToken_ReturnsOK() {
        String validToken = "valid.token";
        lenient().when(userService.validateToken(validToken)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = userController.validateToken(validToken);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
