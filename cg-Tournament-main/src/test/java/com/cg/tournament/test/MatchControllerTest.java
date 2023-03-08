package com.cg.tournament.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.tournament.controllers.MatchesController;
import com.cg.tournament.exceptions.BadRequestException;
import com.cg.tournament.exceptions.UnauthorizedException;
import com.cg.tournament.models.Match;
import com.cg.tournament.services.MatchService;

public class MatchControllerTest {
    
    @Mock
    private MatchService matchService;
    
    @InjectMocks
    private MatchesController matchesController;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddMatch() {
        // Given
    	Match match = new Match();
    	match.setId(1L);
    	match.setFirstPlayerId(1L);
    	match.setSecondPlayerId(2L);
    	match.setRoundId(1L);
    	match.setTableNumber(1);
 when(matchService.addMatch(match)).thenReturn(match);
        
        // When
        ResponseEntity<?> responseEntity = matchesController.addMatch(match);
        
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(match);
    }
    
    @Test
    void testAddMatch_withNullInput() {
        // Given
        Match match = null;
        
        // When
        ResponseEntity<?> responseEntity = matchesController.addMatch(match);
        
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAddMatch_withExistingRoundId() {
        // Given
    	Match match = new Match();
    	match.setId(1L);
    	match.setFirstPlayerId(1L);
    	match.setSecondPlayerId(2L);
    	match.setRoundId(1L);
    	match.setTableNumber(1);
    	when(matchService.addMatch(match)).thenReturn(null);
        
        // When
        ResponseEntity<?> responseEntity = matchesController.addMatch(match);
        
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    void testDeleteMatch() {
        // Given
        Long id = 1L;
        when(matchService.deleteMatch(id)).thenReturn(true);
        
        // When
        ResponseEntity<?> responseEntity = matchesController.deleteMatch(id);
        
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("Round Found with the given ID and got Deleted");
    }
    
    @Test
    void testDeleteMatch_withNonExistingId() {
        // Given
        Long id = 1L;
        when(matchService.deleteMatch(id)).thenReturn(false);
        
        // When
        ResponseEntity<?> responseEntity = matchesController.deleteMatch(id);
        
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo("No Round Found with the given ID");
    }
//    
//    @Test
//    void testGet() {
//        // Given
//        Long roundId = 1L;
//        List<Match> matches = Arrays.asList(
//                new Match(1L, 1L, 2L, 1L, 1, null, null, null, null, null, null, null, null, null, null, null),
//                new Match(2L, 1L, 3L, 1L, 1, null, null, null, null, null, null, null, null, null, null, null));
//                when(matchService.getMatchesByRound(roundId)).thenReturn(matches);
//                // When
//                ResponseEntity<?> responseEntity = matchesController.get(roundId);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//                assertThat(responseEntity.getBody()).isEqualTo(matches);
//            }
//
//            @Test
//            void testGet_withNonExistingRoundId() {
//                // Given
//                Long roundId = 1L;
//                when(matchService.getMatchesByRound(roundId)).thenReturn(null);
//                
//                // When
//                ResponseEntity<?> responseEntity = matchesController.get(roundId);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//                assertThat(responseEntity.getBody()).isEqualTo("No Matches Found for the given Round ID");
//            }
//
//            @Test
//            void testGetMatch() {
//                // Given
//                Long id = 1L;
//                Match match = new Match(1L, 1L, 2L, 1L, 1, null, null, null, null, null, null, null, null, null, null, null);
//                when(matchService.getMatchById(id)).thenReturn(Optional.of(match));
//                
//                // When
//                ResponseEntity<?> responseEntity = matchesController.getMatch(id);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//                assertThat(responseEntity.getBody()).isEqualTo(match);
//            }
//
//            @Test
//            void testGetMatch_withNonExistingId() {
//                // Given
//                Long id = 1L;
//                when(matchService.getMatchById(id)).thenReturn(Optional.empty());
//                
//                // When
//                ResponseEntity<?> responseEntity = matchesController.getMatch(id);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//                assertThat(responseEntity.getBody()).isEqualTo("No Match Found with the given ID");
//            }
//
//            @Test
//            void testUpdateMatch() {
//                // Given
//                Long id = 1L;
//                Match match = new Match(1L, 1L, 2L, 1L, 1, null, null, null, null, null, null, null, null, null, null, null);
//                when(matchService.updateMatch(id, match)).thenReturn(match);
//                
//                // When
//                ResponseEntity<?> responseEntity = matchesController.updateMatch(id, match);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//                assertThat(responseEntity.getBody()).isEqualTo(match);
//            }
//
//            @Test
//            void testUpdateMatch_withNonExistingId() {
//                // Given
//                Long id = 1L;
//                Match match = new Match(1L, 1L, 2L, 1L, 1, null, null, null, null, null, null, null, null, null, null, null);
//                when(matchService.updateMatch(id, match)).thenReturn(null);
//                
//                // When
//                ResponseEntity<?> responseEntity = matchesController.updateMatch(id, match);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            }
//
//            @Test
//            void testUpdateMatch_withNullInput() {
//                // Given
//                Long id = 1L;
//                Match match = null;
//                
//                // When
//                ResponseEntity<?> responseEntity = matchesController.updateMatch(id, match);
//                
//                // Then
//                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//            }
}
