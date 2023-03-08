package com.cg.tournament.services;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.tournament.Constants;
import com.cg.tournament.DAO.MatchesRepository;
import com.cg.tournament.DAO.PlayerRepository;
import com.cg.tournament.DAO.TournamentRepository;
import com.cg.tournament.DAO.UserRepository;
import com.cg.tournament.DTOs.PlayerScore;
import com.cg.tournament.DTOs.comparators.PlayerScoreComparator;
import com.cg.tournament.models.Match;
import com.cg.tournament.models.Player;
import com.cg.tournament.models.Tournament;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerService {
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MatchesRepository matchesRepository;
    @Autowired
    PlayerRepository playerRepository;

    public List<Player> getPlayerNamesByTournamentId(Long id) {
        List<Player> players = playerRepository.findByTournamentId(id);
        List<Player> confirmedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.isConfirmed()) {
                confirmedPlayers.add(player);
            }
        }
        return confirmedPlayers;
    }


    public List<PlayerScore> getPlayersByTournamentId(Long id) {

        List<Player> players = playerRepository.findByTournamentId(id);

        Tournament tournament = tournamentRepository.findById(id).get();

        List<PlayerScore> playerScores = new ArrayList<>();

        for (Player player : players) {
            PlayerScore playerScore = new PlayerScore();
            playerScore.setId(player.getId());
            playerScore.setUserId(player.getUserId());
            playerScore.setName(tournament.isUseFirstLast() || player.getUserId() == null ? player.getDisplayName() : userRepository.getById(player.getUserId()).getUsername());
            int wins = 0;
            int losses = 0;
            int draws = 0;



            List<Match> matchs = matchesRepository.findByPlayerId(player.getId());

            for (Match match : matchs) {
                if (match.getFirstPlayerId().equals(player.getId())) {
                    if (! (match.getMatchResultFirstPlayer()==null)) {
                        if (match.getMatchResultFirstPlayer().equals("WIN")) wins++;
                        else if (match.getMatchResultFirstPlayer().equals("DRAW")) draws++;
                        else losses++;
                    }
                } else if (! (match.getMatchResultSecondPlayer()==null)) {
                    if (match.getMatchResultSecondPlayer().equals("WIN")) wins++;
                    else if (match.getMatchResultSecondPlayer().equals("DRAW")) draws++;
                    else losses++;
                }
            }
            playerScore.setWins(wins);
            playerScore.setLosses(losses);
            playerScore.setDraws(draws);
            playerScore.setMatchPoints(wins * tournament.getWinPoints() + draws * tournament.getDrawPoints() + losses * tournament.getLossPoints());

            playerScore.setFirstTiebreaker(getTiebreakerScore(tournament.getFirstTiebreaker(), player));
            playerScore.setSecondTiebreaker(getTiebreakerScore(tournament.getSecondTiebreaker(), player));
            playerScore.setThirdTiebreaker(getTiebreakerScore(tournament.getThirdTiebreaker(), player));
            playerScore.setFourthTiebreaker(getTiebreakerScore(tournament.getFourthTiebreaker(), player));
            playerScore.setFifthTiebreaker(getTiebreakerScore(tournament.getFifthTiebreaker(), player));

            playerScores.add(playerScore);
        }

        if (playerScores.size() > 1) {
            playerScores.sort(new PlayerScoreComparator());

            int rank = 1;
            int countSame = 0;
            playerScores.get(0).setRank(1);

            for (int i = 0; i < playerScores.size() - 1; i++) {
                if (playerScores.get(i).compareTo(playerScores.get(i + 1)) == 0) {
                    countSame++;
                    playerScores.get(i + 1).setRank(rank);
                } else {
                    rank += countSame + 1;
                    playerScores.get(i + 1).setRank(rank);
                    countSame = 0;
                }
            }
        }
        return playerScores;
    }
    public Player addPlayer(Player player) {
        // TODO: Add user ID to the player object before saving
    	Player playerOptional = playerRepository.findByUserId(player.getUserId());
    	System.out.println(player.getId());
        if (playerOptional != null) {
            return null;
        }

        return playerRepository.save(player);
    }
    
    public Player updatePlayer(Player updatedPlayer) {
        Optional<Player> playerOptional = playerRepository.findById(updatedPlayer.getId());

        if (!playerOptional.isPresent()) {
            return null;
        }

        return playerRepository.save(updatedPlayer);
    }

    public boolean deletePlayer(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);

        if (!playerOptional.isPresent()) {
            return false;
        }

        playerRepository.deleteById(id);
        return true;
    }


    private Double getTiebreakerScore(int tiebreaker, Player player) {
        switch (tiebreaker) {
            case Constants.NONE:
                return 0.0;
            case Constants.OMWP:
                return getOpponentMatchWinPercentage(player);
//            case Constants.MEDIAN_BUCHHOLZ:
//                return getMedianBuchholz(player);
//                break;
//            case Constants.GWP:
//                return getGWP(player);
//                break;
//            case Constants.OGWP:
//                return getOGWP(player);
//                break;
//            case Constants.CUSTOM_A:
//                return getCustomA(player);
//                break;
//            case Constants.CUSTOM_B:
//                return getCustomB(player);
//                break;
            default:
                return 0.0;
        }
    }

    private Double getOpponentMatchWinPercentage(Player player) {
        List<Match> matchs = matchesRepository.findByPlayerId(player.getId());
        double sum = 0.0;
        for (Match match : matchs) {
            if (match.getFirstPlayerId().equals(player.getId())) {
                sum += getMatchWinPercentage(playerRepository.getById(match.getSecondPlayerId()));
            }
            else {
                sum += getMatchWinPercentage(playerRepository.getById(match.getFirstPlayerId()));
            }
        }
        return DoubleRounder.round(sum / matchs.size(), 6);
    }

    public Double getMatchWinPercentage(Player player) {
        List<Match> matchs = matchesRepository.findByPlayerId(player.getId());
        double sum = 0.0;
        for (Match match : matchs) {
            if (match.getFirstPlayerId().equals(player.getId())) {
                if (!(match.getMatchResultFirstPlayer() == null || match.getMatchResultSecondPlayer() == null)) {
                    if (match.getMatchResultFirstPlayer().equals("WIN")) sum += 1;
                }
            } else {
                if (!(match.getMatchResultFirstPlayer() == null || match.getMatchResultSecondPlayer() == null)) {
                    if (match.getMatchResultSecondPlayer().equals("WIN")) sum += 1;
                }
            }
        }
        return sum / matchs.size();
    }
    
    public PlayerScore getPlayerScoreByPlayerIdAndTournamentId(Long playerId, Long tournamentId) {
        List<PlayerScore> playerScores = getPlayersByTournamentId(tournamentId);
        for (PlayerScore playerScore : playerScores) {
            if (playerScore.getId().equals(playerId)) {
                return playerScore;
            }
        }
        return null;
    }

}
