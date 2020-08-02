package Domain.SeasonManagment;

import Domain.Events.AGameEvent;
import Domain.Events.Foul;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import Domain.Users.TeamOwner;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {


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
    }


    @Test
    public void changeDate() {
        Date newDate = new Date(2020, 04, 21);
        game.changeDate(newDate);
        assertEquals(newDate, game.getDateGame());
    }


    @Test
    public void addReferees() {
        List<Game> result = refereeMain.getGames();
        assertEquals(1, result.size());
    }

    // FIXME: 2020-04-22
    @Test
    public void notifyReferees() {

    }

    @Test
    public void notifyTeamfans() {
    }






    @Test
    public void getAway() {
        boolean flag = false;
        if (teamAway.equals(game.getAway())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getHome() {
        boolean flag = false;
        if (teamHome.equals(game.getHome())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getDateGame() {
        boolean flag = false;
        if (date.equals(game.getDateGame())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getMainReferee() {
        boolean flag = false;
        if (refereeMain.equals(game.getMainReferee())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getSeconderyReferee() {
        boolean flag = false;
        if (refereeSec.equals(game.getSeconderyReferee())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getScoreHome() {
        game.getHome().setScore(100);
        boolean flag = false;
        if (100 == game.getHome().getScore()){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getScoreAway() {
        game.getAway().setScore(200);
        boolean flag = false;
        if (200 == game.getAway().getScore()){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getSeason() {
        boolean flag = false;
        if (season.equals(game.getSeason())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void getEvent_logger() {
        boolean flag = false;
        if (game.event_logger.equals(game.getEvent_logger())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setAway() {
        game.setAway(teamHome);
        boolean flag = false;
        if (teamHome.equals(game.getAway())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setHome() {
        game.setHome(teamAway);
        boolean flag = false;
        if (teamAway.equals(game.getAway())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setDateGame() {
        Date date1 = new Date(1995, 9, 9);
        game.setDateGame(date1);
        boolean flag = false;
        if (date1.equals(game.getDateGame())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setMainReferee() {
        game.setMainReferee(refereeSec);
        boolean flag = false;
        if (refereeSec.equals(game.getMainReferee())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setSeconderyReferee() {
        game.setSeconderyReferee(refereeMain);
        boolean flag = false;
        if (refereeMain.equals(game.getSeconderyReferee())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setScoreHome() {
        game.setScoreHome(100);
        boolean flag = false;
        if (100 == (game.getScoreHome())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setScoreAway() {
        game.setScoreAway(1400);
        boolean flag = false;
        if (1400 == (game.getScoreAway())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setSeason() {
        Season season1 = new Season(1989);
        game.setSeason(season1);
        boolean flag = false;
        if (season1.equals(game.getSeason())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setEvent_logger() {
    }


    /*
    @Test
    public void addEventToEventLog() {
        game.addEventToEventLog(foul);
        assertTrue(game.getEvent_logger().getEvents().contains(foul));

    }
    */



}