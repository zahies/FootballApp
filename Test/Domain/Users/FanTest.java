package Domain.Users;

import Domain.Alerts.IAlert;
import Domain.Alerts.PersonalPageAlert;
import Domain.FootballManagmentSystem;
import Domain.PersonalPages.ProfileContent;
import Domain.Searcher.SearchByCategory;
import Domain.Searcher.SearchByKeyword;
import Domain.Searcher.SearchByName;
import Domain.Searcher.Searcher;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import FootballExceptions.*;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FanTest {
    private Fan fanTest;
    private Player p,p2;
    private List<PersonalInfo> infos;
    private HashMap<PersonalInfo,Boolean> pageToFollowTest;
    private Game game,game2;
    private IAlert alert;
    private Team team,team2;
    private ProfileContent profileContent;
    private TeamOwner owner,owner2;
    private Referee refereeMain,refereeSec;
    private ComplaintForm form;
    private SearchByName byName;
    private Member futureManager,futureManager2;

    @Before
    public void init(){
        fanTest = new Fan("great","Noa",4325,"1234");
        futureManager = new Fan("manager","mani",4325,"1234");
        futureManager2 = new Fan("manager2","mani2",43252,"12342");
        p = new Player("adam11","Adam",4321,"0000",3,null,null);
        p2 = new Player("lior4","Lior",4322,"0101",4,null,null);
        owner = new TeamOwner("asif","Asif",333,"234");
        owner2 = new TeamOwner("zach","zach",333,"234");
        team = new Team("null",owner);
        team2 = new Team("zachTeam",owner2);
        infos = new ArrayList<>();
        pageToFollowTest = new HashMap<>();
        refereeMain = new Referee("Rafi","rafi",2424,"354",RefereeType.Main);
        refereeSec = new Referee("Raf","raf",2424,"354",RefereeType.Secondary);
        game= new Game(team,team,null,refereeMain,refereeSec,null);
        game2= new Game(team2,team2,null,refereeMain,refereeSec,null);
        profileContent = new ProfileContent();
        form = new ComplaintForm("I do tests all day");
        byName = new SearchByName();
    }


    @Test
    public void logOut(){
        Member logoutFan = new Fan( "Mourinho", "zoze mourinho", 45564, "GDS3543");
        logoutFan.logOut();
        assertFalse(logoutFan.isActive());
    }
    @Test
    public void update() throws InactiveTeamException, UnauthorizedTeamManagerException, MemberIsAlreadyTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, UnauthorizedTeamOwnerException, AlreadyFollowThisPageException {
        addPersonalPagesToFollow();
        alert = new PersonalPageAlert(p.getInfo(),profileContent);
        fanTest.update(p.getInfo(),alert);
        assertTrue(fanTest.getAlertsList().contains(alert));
    }

    @Test
    public void update2() throws InactiveTeamException, UnauthorizedTeamManagerException, MemberIsAlreadyTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, UnauthorizedTeamOwnerException, TeamOwnerWithNoTeamException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException, AlreadyFollowThisPageException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        owner2.assignNewTeamManager(futureManager2,5);
        futureManager2 = system.getMemberInstanceByKind(futureManager2.getName(),"Team Manager");
        owner2.editManagerPermissions(futureManager2,"Create Personal Page",true);
        ((TeamManager)futureManager2).createPersonalPageForTeam();
        infos.add(team2.getInfo());
        fanTest.addPersonalPagesToFollow(infos);
        pageToFollowTest = fanTest.getPersonalPagesFollowed();
        fanTest.turnAlertForPersonalPageOn(team2.getInfo());
        alert = new PersonalPageAlert(team2.getInfo(),profileContent);
        fanTest.update(game2,alert);
        assertTrue(fanTest.getAlertsList().contains(alert));
        fanTest.turnAlertForPersonalPageOn(team2.getInfo());
        alert = new PersonalPageAlert(team2.getInfo(),profileContent);
        fanTest.update(game2,alert);
        assertTrue(fanTest.getAlertsList().contains(alert));
    }
    @Test
    public void useRecommandationSystem(){
    }

    @Test
    public void addPersonalPagesToFollow() throws InactiveTeamException, UnauthorizedTeamManagerException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, AlreadyFollowThisPageException {
        p.createPersonalPage();
        infos.add(p.getInfo());
        fanTest.addPersonalPagesToFollow(infos);
        pageToFollowTest = fanTest.getPersonalPagesFollowed();
        for (PersonalInfo info : pageToFollowTest.keySet()) {
            assertEquals(p.getInfo(), info);
        }
    }
    @Test
    public void addPersonalPagesToFollow2() throws InactiveTeamException, UnauthorizedTeamManagerException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, UnauthorizedPageOwnerException, TeamOwnerWithNoTeamException, PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException, AlreadyFollowThisPageException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        owner.assignNewTeamManager(futureManager,5);
        futureManager = system.getMemberInstanceByKind(futureManager.getName(),"Team Manager");
        owner.editManagerPermissions(futureManager,"Create Personal Page",true);
        ((TeamManager)futureManager).createPersonalPageForTeam();
        infos.add(team.getInfo());
        fanTest.addPersonalPagesToFollow(infos);
        pageToFollowTest = fanTest.getPersonalPagesFollowed();
        for (PersonalInfo info : pageToFollowTest.keySet()) {
            assertEquals(team.getInfo(), info);
        }
    }

    @Test
    public void unFollowPage() throws AlreadyFollowThisPageException {
        p2.createPersonalPage();
        infos.add(p2.getInfo());
        fanTest.addPersonalPagesToFollow(infos);
        fanTest.unFollowPage(p2.getInfo());
        pageToFollowTest = fanTest.getPersonalPagesFollowed();
        assertEquals(0,pageToFollowTest.size());
    }

    @Test
    public void turnAlertForPersonalPageOn() {
        boolean ans = fanTest.turnAlertForPersonalPageOn(p.getInfo());
        assertFalse(ans);
    }

    @Test
    public void turnAlertForPersonalPageOn2() throws AlreadyFollowThisPageException {
        infos.clear();
        p2.createPersonalPage();
        infos.add(p2.getInfo());
        fanTest.addPersonalPagesToFollow(infos);
        boolean ans = fanTest.turnAlertForPersonalPageOn(p2.getInfo());
        assertTrue(ans);
    }

    @Test
    public void submitComplaintForm() {
        fanTest.submitComplaintForm(form);
    }

    @Test
    public void viewSearchHistory() {
        HashSet<Object> result = new HashSet<>();
        List realResult = new LinkedList();
        result = fanTest.search("Adam",byName);
        realResult = fanTest.viewSearchHistory();
        assertEquals(result.size(),realResult.size());
    }

    @Test
    public void viewPersonalDetails() {
        ArrayList<Pair<String,String>> details= new ArrayList<>();
        details = fanTest.viewPersonalDetails();
        assertEquals(fanTest.getName(),details.get(0).getValue());
        assertEquals(fanTest.getPassword(),details.get(1).getValue());
    }

    @Test
    public void changePassword() throws UserInformationException {
        Fan fanTest3 = new Fan("noala","Noa",4325,"1234");
        fanTest3.changePassword("1111");
        assertEquals("1111",fanTest3.getPassword());
    }

    @Test
    public void changeUserName() throws UserInformationException {
        Fan fanTest2 = new Fan("noala","Noa",4325,"1234");
        fanTest2.changeUserName("Tikva");
        assertEquals("Tikva",fanTest2.getName());
    }

    @Test
    public void view() {
        fanTest.view(p.getInfo());
    }

    @Test
    public void search() {
        HashSet<Object> result = new HashSet<>();
        result = fanTest.search("Adam",byName);
        assertEquals(1,result.size());
    }

    @Test
    public void search2() {
        HashSet<Object> result2 = new HashSet<>();
        Fan fanSearch = new Fan("REWDSA","dasdAS",213213,"e12e12e");
        Searcher search2key = new SearchByKeyword();
        Searcher searchCata = new SearchByCategory();
        result2 = fanTest.search("Adam",search2key);
        assertEquals(1,result2.size());
    }

    @Test
    public void setPersonalPagesFollowed(){
        HashMap<PersonalInfo,Boolean> infoStringHashMap = new HashMap<>();
        infoStringHashMap.put(p.getInfo(),true);
        fanTest.setPersonalPagesFollowed(infoStringHashMap);
        assertEquals(infoStringHashMap.size(),fanTest.getPersonalPagesFollowed().size());

    }

    @Test
    public void notifyFan() {
    }
}