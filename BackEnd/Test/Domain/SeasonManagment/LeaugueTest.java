package Domain.SeasonManagment;

import Domain.FootballManagmentSystem;
import Domain.Users.Commissioner;
import FootballExceptions.IDWasNotEnterdException;
import FootballExceptions.LeagueIDAlreadyExist;
import FootballExceptions.SeasonYearAlreadyExist;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LeaugueTest {


    Leaugue leaugue;
    Commissioner commissioner;
    Season season;

    @Before
    public void init(){
        leaugue = new Leaugue();
        commissioner = new Commissioner("ItamarGo",555, "I5T5","Itamar");
        season = new Season(1995);

    }




    @Test
    public void setId1() {
        leaugue.setId(2);
        int ans = leaugue.getID();
        int result = 2;
        assertEquals(result, ans);
    }


    @Test
    public void setId2() {
        leaugue.setId(2);
        int ans = leaugue.getID();
        int result = 3;
        assertNotEquals(result, ans);
    }



    @Test (expected = IDWasNotEnterdException.class)
    public void setLeagueIntoSystem() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        leaugue.setId(0);
        leaugue.setLeagueIntoSystem();
    }


    @Test
    public void setLeagueIntoSystem2() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        leaugue.setId(10);
        leaugue.setLeagueIntoSystem();
        List<Leaugue> result = (FootballManagmentSystem.getInstance()).getAllLeagus();
        assertEquals(12, result.size());
    }




        @Test(expected = SeasonYearAlreadyExist.class)
    public void addSeasonToLeagueByYear() throws SeasonYearAlreadyExist {
        leaugue.addSeasonToLeagueByYear(1995);
        leaugue.addSeasonToLeagueByYear(1995);
    }


    /*
    @Test
    public void setLeagueIntoSystem1() {
        leaugue.setId(1);
    }
*/

}