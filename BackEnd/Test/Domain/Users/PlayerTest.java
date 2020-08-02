package Domain.Users;

import Domain.Events.*;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Team;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player playerTest;
    private Player playerTest2;
    private PersonalInfo info;
    private ProfileContent profileContent;
    private Team team;
    private Date date;
    private TeamOwner owner;
    private AGameEvent event;

    @Before
    public void init() {
        owner = new TeamOwner("Tal23","Tal",35,"@3534");
        team = new Team("Haifa",owner);
        playerTest = new Player("Eyal2","Eyal", 1234, "0000", 4, null, null);
        playerTest2 = new Player("guy55","Guy", 1233, "0011", 3, null, null);
        info = new PersonalInfo(playerTest);
        profileContent = new ProfileContent();

        date = new Date();
    }

    @Test
    public void createPersonalPage() {
        playerTest.createPersonalPage();
        assertNotEquals(info.getPageID(), playerTest.getInfo().getPageID());
    }

    @Test
    public void createPersonalPage2() {
        playerTest.setInfo(info);
        playerTest.createPersonalPage();
        assertNotEquals(info.getPageID(), playerTest.getInfo().getPageID());

    }

    @Test
    public void addContentToPersonalPage() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        playerTest.createPersonalPage();
        boolean ans = playerTest.addContentToPersonalPage(profileContent);
        assertTrue(ans);
    }

    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void addContentToPersonalPage2() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        playerTest2.addContentToPersonalPage(profileContent);
    }

    @Test
    public void editProfile() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        playerTest.createPersonalPage();
        playerTest.addContentToPersonalPage(profileContent);
        boolean ans = playerTest.editProfile("height", "1.80");
        assertTrue(ans);
    }

    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void editProfile2() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        playerTest2.createPersonalPage();
        playerTest2.editProfile("height","1.80");

    }

    @Test
    //+getValue
    public void changePlayerRate() {
        event = new Foul(40,playerTest);
        double beforeChange = playerTest.getFootballRate();
        playerTest.changePlayerRate(event);
        assertNotEquals((int)beforeChange,(int)playerTest.getFootballRate());

    }
    @Test
    public void changePlayerRate2() {
        event = new Goal(40,playerTest);
        double beforeChange = playerTest.getFootballRate();
        playerTest.changePlayerRate(event);
        assertNotEquals(beforeChange,playerTest.getFootballRate(),0);

    }

    @Test
    public void changePlayerRate3() {
        event = new OffSide(40,playerTest);
        double beforeChange = playerTest.getFootballRate();
        playerTest.changePlayerRate(event);
        assertNotEquals((int)beforeChange,(int)playerTest.getFootballRate());

    }
    @Test
    public void changePlayerRate4() {
        event = new RedCard(40,playerTest);
        double beforeChange = playerTest.getFootballRate();
        playerTest.changePlayerRate(event);
        assertNotEquals((int)beforeChange,(int)playerTest.getFootballRate());
    }
    @Test
    public void changePlayerRate5() {
        playerTest.setMyTeam(team);
        event = new YellowCard(40,playerTest);
        double beforeChange = playerTest.getFootballRate();
        playerTest.changePlayerRate(event);
        assertNotEquals((int)beforeChange,(int)playerTest.getFootballRate());
    }

    @Test
    //+getMyTeam
    public void setMyTeam() {
        playerTest.setMyTeam(team);
        assertEquals(team.getId(),playerTest.getMyTeam().getId());
    }

    @Test
    //+getRole
    public void setRole() {
        playerTest.setRole("Goalkeeper");
        assertEquals("Goalkeeper",playerTest.getRole());
    }


    @Test
    //+getInfo
    public void setInfo() {
        playerTest.setInfo(info);
        assertEquals(info.getPageID(),playerTest.getInfo().getPageID());
    }

    @Test
    //+getDateOfBirth
    public void setDateOfBirth() {
        playerTest.setDateOfBirth(date);
        assertEquals(date,playerTest.getDateOfBirth());
    }
    @Test
    //+getValue
    public void edit() {
        playerTest.edit(5);
        assertEquals(5, playerTest.getValue());
    }
    @Test
    public void getFootballRate(){
        playerTest.getFootballRate();
    }

}

