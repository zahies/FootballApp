package Domain.SeasonManagment;

import Domain.Events.AGameEvent;
import Domain.Events.Foul;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import Domain.Users.TeamOwner;
import FootballExceptions.InactiveTeamException;
import FootballExceptions.UnauthorizedTeamOwnerException;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class RecommendationSystemTest {


    TeamOwner teamOwner;
    Team teamAway;
    Team teamHome;
    Date date;
    Referee refereeSec;
    Referee refereeMain;
    Season season;
    Game game;
    Player player;
    AGameEvent foul;
    RecommendationSystem recommendationSystem;



    @Before
    public void init() {
        teamOwner = new TeamOwner("Yoyo", "Yosi", 789, "Y8O8", 555);
        teamAway = new Team("Backstreet Boys", teamOwner);
        teamHome = new Team("Golden Boys", teamOwner);
        date = new Date(2020, 04, 22);
        refereeSec = new Referee("Tomos", "Tom", 567, "T5O6", RefereeType.Secondary);
        refereeMain = new Referee("Yomos", "Yom", 566, "Y5O6", RefereeType.Main);
        season = new Season(2020);
        game = new Game(teamAway,teamHome,date, refereeMain,refereeSec,season);
        player = new Player("Polo","Apolo",333, "A3P3", 1,"Player", date);
        foul = new Foul(54, player);
        recommendationSystem = new RecommendationSystem();
    }


    @Test
    public void recommendTeamOddsOfWinning1() throws InactiveTeamException, UnauthorizedTeamOwnerException {
        teamAway.addBudgetActivity(teamOwner, new Date(), BudgetActivity.BuyPlayer, 200);
        teamHome.addBudgetActivity(teamOwner, new Date(), BudgetActivity.BuyPlayer, 400);
        teamAway.getPlayersFootballRate();
        recommendationSystem.recommendTeamOddsOfWinning(game, teamAway);

    }
}