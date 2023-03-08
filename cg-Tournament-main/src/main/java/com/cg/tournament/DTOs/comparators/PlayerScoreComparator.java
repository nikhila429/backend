package com.cg.tournament.DTOs.comparators;

import java.util.Comparator;

import com.cg.tournament.DTOs.PlayerScore;

public class PlayerScoreComparator implements Comparator<PlayerScore> {
    @Override
    public int compare(PlayerScore o1, PlayerScore o2) {
        // * -1 is needed to reverse the order
        return o1.compareTo(o2) * -1;
    }
}
