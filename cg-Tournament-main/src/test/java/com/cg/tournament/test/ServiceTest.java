package com.cg.tournament.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.tournament.DAO.AdminRepository;
import com.cg.tournament.DTOs.AdminWithUsername;
import com.cg.tournament.exceptions.BadRequestException;
import com.cg.tournament.models.Admin;
import com.cg.tournament.services.AdminService;

import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
class ServiceTest {
    
    @Mock
    private AdminRepository adminRepositoryMock;

    @InjectMocks
    private AdminService adminService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("testing admin get response")
    public void testGetAdminsByTournamentId() {
        // Arrange
        Long tournamentId = 1L;
        List<Admin> admins = new ArrayList<Admin>();
        Admin admin1 = new Admin(1L, tournamentId, 1L);
        Admin admin2 = new Admin(2L, tournamentId, 2L);
        admins.add(admin1);
        admins.add(admin2);
        when(adminRepositoryMock.findByTournamentId(tournamentId)).thenReturn(admins);

        // Act
        List<AdminWithUsername> adminsWithUsername = adminService.getAdminsByTournamentId(tournamentId);

        // Assert
        assertEquals(2, adminsWithUsername.size());
        assertEquals(admin1.getId(), adminsWithUsername.get(0).getId());
        assertEquals(admin1.getTournamentId(), adminsWithUsername.get(0).getTournamentId());
        assertEquals(admin1.getUserId(), adminsWithUsername.get(0).getUserId());
        assertEquals(admin2.getId(), adminsWithUsername.get(1).getId());
        assertEquals(admin2.getTournamentId(), adminsWithUsername.get(1).getTournamentId());
        assertEquals(admin2.getUserId(), adminsWithUsername.get(1).getUserId());
    }

    @Test
    public void testAddAdmin() {
        // Arrange
        Long tournamentId = 1L;
        String username = "user1";
        Admin admin = new Admin(1L, tournamentId, 1L);
        when(adminRepositoryMock.save(any(Admin.class))).thenReturn(admin);

        // Act
        AdminWithUsername adminWithUsername = adminService.addAdmin(tournamentId, username);

        // Assert
        assertEquals(admin.getId(), adminWithUsername.getId());
        assertEquals(admin.getTournamentId(), adminWithUsername.getTournamentId());
        assertEquals(admin.getUserId(), adminWithUsername.getUserId());
        assertEquals(username, adminWithUsername.getUsername());
    }

    @Test
    public void testAddAdminInvalidUsername() {
        // Arrange
        Long tournamentId = 1L;
        String username = "user1";
        when(adminRepositoryMock.save(any(Admin.class))).thenThrow(new BadRequestException("Invalid username"));

        // Act
        adminService.addAdmin(tournamentId, username);
    }

    @Test
    public void testDeleteByAdminId() {
        // Arrange
        Long adminId = 1L;

        // Act
        adminService.deleteByAdminId(adminId);

        // Assert
        verify(adminRepositoryMock, times(1)).deleteById(adminId);
    }
}

