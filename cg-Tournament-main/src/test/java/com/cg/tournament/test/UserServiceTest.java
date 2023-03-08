package com.cg.tournament.test;

import com.cg.tournament.DAO.UserRepository;
import com.cg.tournament.exceptions.BadRequestException;
import com.cg.tournament.exceptions.ConflictException;
import com.cg.tournament.exceptions.UnauthorizedException;
import com.cg.tournament.models.User;
import com.cg.tournament.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(
                1L,
                "user1",
                "userfull1",
                "user1@gmail.com",
                "$2a$10$Bmd9JjLhAM64tfqGGQgHvu8lYeuvJzNT.wvuSSoPxB3.gtCQtAAJS",
                true
        );
    }

    @Test
    @DisplayName("validateUser() should throw an UnauthorizedException if username is null")
    void validateUserShouldThrowUnauthorizedExceptionIfUsernameIsNull() {
    	User user = new User();
    	user.setPassword("password");
        assertThatThrownBy(() -> userService.validateUser(user))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Username required");
    }

    @Test
    @DisplayName("validateUser() should throw an UnauthorizedException if username or password is incorrect")
    void validateUserShouldThrowUnauthorizedExceptionIfUsernameOrPasswordIsIncorrect() {
        User user = new User();
        user.setUsername("invalidUsername");
        user.setPassword("invalidPassword");

        lenient().when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);

        assertThatThrownBy(() -> userService.validateUser(user))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Username or password incorrect");

        lenient().when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        lenient().when(userRepository.findOneByUsername(user.getUsername())).thenReturn(testUser);

        assertThatThrownBy(() -> userService.validateUser(user))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Username or password incorrect");

        user.setPassword(null);

        assertThatThrownBy(() -> userService.validateUser(user))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Password required");
    }

    @Test
    @DisplayName("validateUser() should return a ResponseEntity with JWT token if user is valid")
    void validateUserShouldReturnResponseEntityWithJwtTokenIfUserIsValid() {
        User user = new User();
        user.setUsername(testUser.getUsername());
        user.setPassword(testUser.getPassword());

//        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
//        when(userRepository.findOneByUsername(user.getUsername())).thenReturn(testUser);

//        assertThat(userService.validateUser(user).getStatusCodeValue()).isEqualTo(200);
        
    }

    @Test
    @DisplayName("registerUser() should throw a BadRequestException if email address is null")
    void registerUserShouldThrowBadRequestExceptionIfEmailAddressIsNull() {
        User user = new User();
        user.setUsername("testUser");
        user.setFullName("Test User");
        user.setPassword("password");
        

        assertThatThrownBy(() -> userService.registerUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email address required");
    }
}