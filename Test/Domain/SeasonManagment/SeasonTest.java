package Domain.SeasonManagment;

import Domain.Users.Referee;
import Domain.Users.RefereeType;
import Domain.Users.TeamOwner;
import FootballExceptions.NotEnoughTeamsInLeague;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.*;

public class SeasonTest {


    IScorePolicy iScorePolicy;
    IPlaceTeamsPolicy iPlaceTeamsPolicy;
    Season season1;
    Season season ;
    Referee refereeSec;
    Referee refereeSec1;
    Referee refereeMain;
    Referee refereeMain1;
    TeamOwner teamOwner;
    Team teamAway;
    Team teamHome;


    @Before
    public void init(){
        iScorePolicy = new DefaultIScorePolicy();
        iPlaceTeamsPolicy = new DefaultTeamsPolicy();
        season1 = new Season(iScorePolicy,iPlaceTeamsPolicy,2020);
        season = new Season(2000);
        refereeSec = new Referee("Tomos", "Tom", 567, "T5O6", RefereeType.Secondary);
        refereeSec1 = new Referee("Tomos1", "Tom", 567, "T5O6", RefereeType.Secondary);
        refereeMain = new Referee("Yomos", "Yom", 566, "Y5O6", RefereeType.Main);
        refereeMain1 = new Referee("Yomos1", "Yom", 566, "Y5O6", RefereeType.Main);
        teamOwner = new TeamOwner("Yoyo", "Yosi", 789, "Y8O8", UUID.randomUUID());
        teamAway = new Team("Backstreet Boys", teamOwner);
        teamHome = new Team("Golden Boys", teamOwner);
    }

    @Test
    public void isItTheBeginningOfSeason() {
        boolean result = season.isItTheBeginningOfSeason();
        assertTrue(result);
    }

    @Test
    public void setItTheBeginningOfSeason() {
        season.setItTheBeginningOfSeason(false);
        assertFalse(season.isItTheBeginningOfSeason());
    }

    @Test
    public void getScorePolicy() {
        season.setScorePolicy(new DefaultIScorePolicy());
        boolean flag = false;
        if (season.getScorePolicy() instanceof DefaultIScorePolicy){
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void setScorePolicy() {

    }

    @Test
    public void getPlaceTeamsPolicy() {
        season.setPlaceTeamsPolicy(new DefaultTeamsPolicy());
        boolean flag = false;
        if (season.getPlaceTeamsPolicy() instanceof DefaultTeamsPolicy){
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void setPlaceTeamsPolicy() {
    }

    @Test
    public void getYear() {
        season.setYear(2005);
        boolean flag = false;
        if (season.getYear() == 2005){
            flag = true;
        }
        assertTrue(flag);

    }

    @Test
    public void setYear() {
    }



    @Test
    public void getScore_teams() {
        season.addTeamToSeason(teamAway);
        season.setScore_teams(100, teamAway);
        LinkedList<Pair<Integer,Team>> teams = season.getScore_teams();
        Pair<Integer,Team> result = teams.getFirst();
        assertEquals(100, (int)result.getKey());
    }



    @Test
    public void setScore_teams() {
    }



    @Test
    public void getReferees() {
    }

    @Test
    public void setReferees() {
    }

    @Test
    public void deleteRefereeFromSeasonByName() {
        season.addRefereeToSeason(refereeMain);
        season.deleteRefereeFromSeasonByName(null);
        assertEquals(1, season.getReferees().size());
    }

    @Test
    public void deleteRefereeFromSeasonByName1() {
        season.addRefereeToSeason(refereeMain);
        season.deleteRefereeFromSeasonByName(refereeMain.getName());
        assertEquals(0, season.getReferees().size());
    }


    @Test
    public void addRefereeToSeason() {
        season.addRefereeToSeason(refereeMain);
        assertEquals(1,season.getReferees().size());
    }

    @Test
    public void addRefereeToSeason1() {
        season.addRefereeToSeason(refereeMain);
        season.addRefereeToSeason(refereeMain);
        assertEquals(1,season.getReferees().size());
    }

    @Test
    public void addRefereeToSeason2() {
        season.addRefereeToSeason(refereeMain);
        season.addRefereeToSeason(refereeSec);
        assertEquals(2,season.getReferees().size());
    }

    @Test
    public void addRefereeToSeason3() {
        season.addRefereeToSeason(null);
        assertEquals(0,season.getReferees().size());
    }

    @Test
    public void setNewScorePolicy() {
        season.setNewScorePolicy(iScorePolicy);
        boolean flag=false;
        if(season.getScorePolicy() instanceof  DefaultIScorePolicy)
            flag=true;
        assertTrue(flag);
    }

    @Test
    public void setNewTeamsPolicy() {
        season.setNewTeamsPolicy(iPlaceTeamsPolicy);
        boolean flag=false;
        if(season.getPlaceTeamsPolicy() instanceof  DefaultTeamsPolicy)
            flag=true;
        assertTrue(flag);
    }

    @Test(expected = NotEnoughTeamsInLeague.class)
    public void runPlacingTeamsAlgorithm() throws NotEnoughTeamsInLeague {
        season.runPlacingTeamsAlgorithm();
    }




    // FIXME: 2020-04-22 
    @Test
    public void addTeamToSeason(){
        season.addTeamToSeason(teamAway);
     //   season.addTeamToSeason(teamAway);
        season.addTeamToSeason(teamHome);
        assertEquals(2,(season.getScore_teams()).size());
    }


    @Test
    public void getRefereesToGame() {
        season.addRefereeToSeason(refereeSec);
        season.addRefereeToSeason(refereeSec1);
        season.addRefereeToSeason(refereeMain);
        season.addRefereeToSeason(refereeMain1);
        assertEquals(2,(season.getRefereesToGame()).length);
    }


    @Test
    public void getRefereesToGame1() {
        season.addRefereeToSeason(refereeSec);
        assertNull(season.getRefereesToGame());
    }

    @Test
    public void getRefereesToGame2() {
        assertNull(season.getRefereesToGame());
    }


    @Test
    public void getRefereesToGame3() {
        season.addRefereeToSeason(refereeSec);
        season.addRefereeToSeason(refereeSec);
        season.addRefereeToSeason(refereeSec);
        assertNull(season.getRefereesToGame());
    }

/*

    @Test
    public void runPlacingTeamsAlgorithm1() throws NotEnoughTeamsInLeague {
        season.addTeamToSeason(teamAway);
        season.addTeamToSeason(teamHome);
        season.addRefereeToSeason(refereeMain);
        season.addRefereeToSeason(refereeSec);
        season.runPlacingTeamsAlgorithm();
    }


 */

}