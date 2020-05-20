package Domain.SeasonManagment;

import Domain.Users.TeamOwner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {


    TeamOwner teamOwner;
    Team teamAway;
    Team teamHome;
    int assetID;
    Field field;


    @Before
    public void init(){
        teamOwner = new TeamOwner("Yoyo", "Yosi", 789, "Y8O8", 555);
        teamAway = new Team("Backstreet Boys", teamOwner);
        teamHome = new Team("Golden Boys", teamOwner);
        assetID = 222;
        field = new Field();
        field.setMyTeam(teamAway);
    }

    //fixme
    @Test
    public void getAssetID() {
        assertEquals(field.getAssetID(), field.getAssetID());
    }



    @Test
    public void edit() {
    }

    @Test
    public void setMyTeam() {
        field.setMyTeam(teamHome);
        boolean flag = false;
        if (teamHome.equals(field.getMyTeam()))
            flag=true;
        assertTrue(flag);
    }

    @Test
    public void getMyTeam() {
    }

    @Test
    public void getValue() {
        assertEquals(0, field.getValue());
    }
}