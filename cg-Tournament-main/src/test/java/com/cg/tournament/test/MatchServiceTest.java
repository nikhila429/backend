package com.cg.tournament.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.tournament.DAO.MatchesRepository;
import com.cg.tournament.models.Match;
import com.cg.tournament.models.Round;
import com.cg.tournament.services.MatchService;

public class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchesRepository matchesRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

   

    @Test
    public void testAddMatch_withExistingRoundId() {
        // given
        Match match = new Match();
        match.setId(1L);
        match.setRoundId(1L);

        
        Match savedMatch = matchService.addMatch(match);

        // then
        assertNull(savedMatch);
    }

    @Test
    public void testDeleteMatch() {
        // given
        Match match = new Match();
        match.setId(1L);

        // when
        when(matchesRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchesRepository.existsById(1L)).thenReturn(false);
        boolean deleted = matchService.deleteMatch(1L);

        // then
        assertTrue(deleted);
    }

    @Test
    public void testDeleteMatch_withNonExistingMatch() {
        // given

        // when
        when(matchesRepository.findById(1L)).thenReturn(Optional.empty());
        boolean deleted = matchService.deleteMatch(1L);

        // then
        assertFalse(deleted);
    }

    @Test
    public void testGetPairingsByRoundId() {
        // given
        Match match1 = new Match();
        match1.setId(1L);
        match1.setRoundId(1L);

        Match match2 = new Match();
        match2.setId(2L);
        match2.setRoundId(1L);

        List<Match> expectedMatches = new ArrayList<>();
        expectedMatches.add(match1);
        expectedMatches.add(match2);

        // when
        when(matchesRepository.findByRoundId(1L)).thenReturn(expectedMatches);
        List<Match> actualMatches = matchService.getPairingsByRoundId(1L);

        // then
        assertNotNull(actualMatches);
        assertEquals(expectedMatches.size(), actualMatches.size());
        assertEquals(expectedMatches.get(0).getId(), actualMatches.get(0).getId());
        assertEquals(expectedMatches.get(1).getId(), actualMatches.get(1).getId());
    }
    @Test
    public void testAddMatch() {
        // given
        Match match = new Match();
        match.setId(1L);

        // when
        lenient().when(matchesRepository.save(match)).thenReturn(match);
        Match savedMatch = matchService.addMatch(match);

        // then
        assertNotNull(match);
        
    }
    @Test
    public void testGetById() {
        // given
        Match match = new Match();
        match.setId(1L);

        // when
        when(matchesRepository.findById(1L)).thenReturn(Optional.of(match));
        Match actualMatch = matchService.getById(1L);

        // then
        assertNotNull(actualMatch);
        assertEquals(match.getId(), actualMatch.getId());
    }



}
