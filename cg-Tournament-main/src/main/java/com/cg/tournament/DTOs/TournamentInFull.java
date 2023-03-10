package com.cg.tournament.DTOs;

import java.util.ArrayList;
import java.util.List;

import com.cg.tournament.models.*;

public class TournamentInFull {

    private TournamentAtAGlance tournament;
    private List<Player> players = new ArrayList<Player>();
    private List<AdminWithUsername> admins = new ArrayList<>();

    public TournamentAtAGlance getTournament() {
        return tournament;
    }

    public void setTournament(TournamentAtAGlance tournament) {
        this.tournament = tournament;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<AdminWithUsername> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminWithUsername> admins) {
        this.admins = admins;
    }

}
