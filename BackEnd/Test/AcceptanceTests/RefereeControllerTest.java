package AcceptanceTests;

import Domain.Events.AGameEvent;
import Domain.Events.Foul;
import Domain.Events.Goal;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import Domain.Users.TeamOwner;
import FootballExceptions.NoPermissionException;
import Service.RefereeController;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class RefereeControllerTest {
    private RefereeController refereeControllerTest;
    private Referee refereeMain,refereeSec;
    private TeamOwner owner;
    private Game game;
    private Team team;
    private TeamOwner teamOwner;
    private Date date;
    private AGameEvent gameEvent1,gameEvent2,gameEvent3,gameEvent4;
    private Player p;

    @Before
    public void init(){
        refereeControllerTest = new RefereeController();
        refereeMain = new Referee("Shahar","Shahar",10101,"@!$", RefereeType.Main);
        refereeSec = new Referee("rami","rami",10101,"@!$", RefereeType.Secondary);
        owner = new TeamOwner("Ohad","ohad",1002,"244");
        team = new Team("Engineer",owner);
        Date date = new Date("30/06/2020");
        game = new Game(team,team,date,refereeMain,refereeSec,null);
        p = new Player("Shir","shir",1003,"325325",6,"null",null);
        gameEvent1 = new Foul(13,p);
        gameEvent2 = new Foul(20,p);
        gameEvent3 = new Goal(50,p);
        gameEvent4 = new Goal(40,p);

    }

    /*positive test*/
    @Test
    public void editEventsAfterGamePositive() throws IOException {
        refereeControllerTest.editEventsAfterGame(refereeMain,game,gameEvent1,gameEvent2);
        assertTrue(game.event_logger.getEvents().contains(gameEvent2));

    }

    /*negative test*/
    @Test
    public void editEventsAfterGameNegative() {
        refereeControllerTest.editEventsAfterGame(refereeSec,game,gameEvent1,gameEvent2);
    }


    @Test
    public void addReportForGamePositive() throws NoPermissionException {
        refereeControllerTest.addReportForGame(refereeMain,game);
        assertTrue(game.getHome().getGameHistoryWithOtherTeam(team).contains(game));
    }

    @Test
    public void addReportForGameNegative() {

        refereeControllerTest.addReportForGame(refereeSec,game);

    }
}