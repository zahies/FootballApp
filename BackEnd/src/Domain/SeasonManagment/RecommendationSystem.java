package Domain.SeasonManagment;

import java.util.LinkedList;

public class RecommendationSystem {

    public double recommendTeamOddsOfWinning(Game game, Team team) {
        double odds = 0;
        double homeTeamPlayersRate = game.getHome().getPlayersFootballRate();
        double awayTeamPlayersRate = game.getAway().getPlayersFootballRate();
        double maxRate = 0;
        if (homeTeamPlayersRate > awayTeamPlayersRate) {
            maxRate = homeTeamPlayersRate;
        } else {
            maxRate = awayTeamPlayersRate;
        }

        int homeTeamBudget = game.getHome().getCurrentBudget();
        int awayTeamBudget = game.getAway().getCurrentBudget();
        int maxBudget = 0;
        if (homeTeamBudget > awayTeamBudget) {
            maxBudget = homeTeamBudget;
        } else {
            maxBudget = awayTeamBudget;
        }

        int numOfWinsForHomeTeam = 0;
        int numOfDraws = 0;
        LinkedList<Game> gamesHistory = game.getHome().getGameHistoryWithOtherTeam(game.getAway());
        for (Game oldGame : gamesHistory) {
            if (oldGame.getScoreAway() == oldGame.getScoreHome()) {
                numOfDraws++;
            } else if (oldGame.getHome().equals(game.getHome())) {
                if (oldGame.getScoreHome() > oldGame.getScoreAway()) {
                    numOfWinsForHomeTeam++;
                }
            } else {
                if (oldGame.getScoreAway() > oldGame.getScoreHome()) {
                    numOfWinsForHomeTeam++;
                }
            }
        }
        int numOfLosesForHomeTeam = gamesHistory.size() - (numOfWinsForHomeTeam + numOfDraws);
        int numOfDrawsForAwayTeam = numOfDraws;
        int numOfWinsForAwayTeam = numOfLosesForHomeTeam;
        if (maxBudget == 0 || maxRate == 0 || gamesHistory.size() == 0) {
            return 100 / 3;
        }
        if (game.getHome().equals(team)) {
            odds = (homeTeamBudget / maxBudget) * 2 + (homeTeamPlayersRate / maxRate) * 4 + ((numOfWinsForHomeTeam + numOfDraws / 2) / gamesHistory.size()) * 4;
            odds = odds + 1; /**home advantage*/
            odds = odds / 11;
        }
        if (game.getAway().equals(team)) {
            odds = (awayTeamBudget / maxBudget) * 2 + (awayTeamPlayersRate / maxRate) * 4 + ((numOfWinsForAwayTeam + numOfDraws / 2) / gamesHistory.size()) * 4;
            odds = odds / 10;
        }
        return odds;
    }
}
