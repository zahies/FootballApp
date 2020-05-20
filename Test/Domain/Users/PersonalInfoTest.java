package Domain.Users;

import Domain.Alerts.GameEventAlert;
import Domain.Alerts.IAlert;
import Domain.Events.Goal;
import Domain.Events.IEvent;
import Domain.PersonalPages.NewsContent;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalInfoTest {
    private PersonalInfo personalInfoTest;
    private TeamManager teamManager;
    private TeamManager teamManager2;
    private TeamManager teamManager3;
    private ProfileContent profileContent;
    private NewsContent newsContent;
    private Team team;
    private Fan fan;
    private TeamOwner owner;
    private IAlert alert;
    private IEvent event;
    private Game game;
    private Referee referee;

    @Before
    public void init(){
        owner = new TeamOwner("Tal23","Tal",35,"@3534");
        team = new Team("Maccabi Haifa",owner);
        teamManager = new TeamManager("Adam12","Adam",1223,"1234",4,team,null);
        teamManager2 = new TeamManager("Gadi56","Gadi",1224,"1234",5,team,null);
        teamManager3 = new TeamManager("Gadi56","Gadi",1224,"1234",5,null,null);
        personalInfoTest = new PersonalInfo(teamManager);
        profileContent = new ProfileContent();
        fan = new Fan("Yarden90","Yarden",1234,"0000");
        newsContent = new NewsContent();
        event = new Goal(30,null);
        alert = new GameEventAlert(30,event);
        referee = new Referee("Tom","tom",355,"345",RefereeType.Main);
        game = new Game(team,team,null,referee,referee,null);

    }
    @Test
    public void addContentToPage() throws UnauthorizedPageOwnerException {
        boolean ans = personalInfoTest.addContentToPage(teamManager, profileContent);
        assertTrue(ans);

        boolean ans2 = personalInfoTest.addContentToPage(teamManager, newsContent);
        assertTrue(ans2);

    }

    @Test(expected = UnauthorizedPageOwnerException.class)
    public void addContentToPage2() throws UnauthorizedPageOwnerException {
        personalInfoTest.addContentToPage(teamManager3, profileContent);

    }


    @Test(expected = UnauthorizedPageOwnerException.class)
    public void editProfile() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        personalInfoTest.editProfile(teamManager3,"height","1.80");
    }

    @Test
    public void editProfile2() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        personalInfoTest.addContentToPage(teamManager, profileContent);
        boolean ans = personalInfoTest.editProfile(teamManager,"height","1.80");
        assertTrue(ans);
    }

    @Test
    public void addFollower() {
        personalInfoTest.addFollower(fan);
        assertEquals(fan.getName(),personalInfoTest.getFollowers().get(0).getName());
    }

    @Test
    public void removeFollower() {
        personalInfoTest.addFollower(fan);
        personalInfoTest.removeFollower(fan);
        assertEquals(0,personalInfoTest.getFollowers().size());

    }

    @Test
    public void notifyInfo() {
        personalInfoTest.addFollower(fan);
        personalInfoTest.notifyInfo(alert,game);
    }

    @Test
    public void isPageOwner() {
        boolean ans = personalInfoTest.isPageOwner(teamManager);
        assertTrue(ans);
        boolean ans2 = personalInfoTest.isPageOwner(teamManager2);
        assertFalse(ans2);

    }

    @Test
    public void viewPersonalPage() {
    }

    @Test
    public void addTeamPageMemberOwner() {
        personalInfoTest.addTeamPageMemberOwner(teamManager);

    }

    @Test
    public void removeOwnerFromPageMemberOwner() {
        personalInfoTest.addTeamPageMemberOwner(teamManager);
        personalInfoTest.removeOwnerFromPageMemberOwner(teamManager);


    }

    @Test
    public void getPageID() {
        assertEquals(personalInfoTest.getPageID(),personalInfoTest.getPageID());
    }
    @Test
    public void getPageMemberOwner() {
        assertEquals(teamManager,personalInfoTest.getPageMemberOwner());
    }
    @Test
    public void getTeamPageMembersOwners(){
        personalInfoTest.getTeamPageMembersOwners();
    }
    @Test
    public void getPageTitle() {
        personalInfoTest.getPageTitle();
    }
    @Test
    public void getProfile(){
        assertEquals(personalInfoTest.getProfile(),personalInfoTest.getProfile());
    }
    @Test
    public void getPageContent(){
        personalInfoTest.getPageContent();

    }
    @Test
    public void getFollowers() {
        personalInfoTest.addFollower(fan);
        assertEquals(fan,personalInfoTest.getFollowers().get(0));

    }
}