package Domain.Users;

import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TeamManagerTest {
    private TeamManager teamManagerTest;
    private Team team,team2,team3,team4;
    private Coach coach;
    private ProfileContent content;
    private HashMap<String, String> profileCon;
    private TeamOwner owner,owner2,owner3,owner4;
    private Member futureManager,futureManager2,futureManager3,futureManager4;

    @Before
    public void setUp() throws Exception {
        owner = new TeamOwner("kfir78","Kfir",2222,"0909");
        owner2 = new TeamOwner("kfir7","Kfiro",222,"090");
        owner3 = new TeamOwner("kfir","Kfirosh",222,"090");
        owner4 = new TeamOwner("kfi","Kfi",222,"090");
        team = new Team("Gadi", owner);
        team2 = new Team("Gad", owner2);
        team3 = new Team("Ga", owner3);
        team4 = new Team("Gana", owner4);
        teamManagerTest = new TeamManager("adam22","Adam", 1111, "5555", 3, team,owner);
        coach = new Coach("lior22","Lior", 1313, "%420",4,"personal",null);
        content = new ProfileContent();
        futureManager = new Player("manager20","naci",2345,"32423",4,null,null);
        futureManager2 = new Player("manager21","naci2",2346,"32423",4,null,null);
        futureManager3 = new Player("manager22","naci3",2347,"32423",4,null,null);
        futureManager4 = new Player("manager23","naci4",2348,"32423",4,null,null);

    }

    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void editPermissions() throws InactiveTeamException, UnauthorizedPageOwnerException, UnauthorizedTeamOwnerException, PersonalPageYetToBeCreatedException {
        teamManagerTest.editPermissions(owner,"Add Content To Personal Page",true);
    }

    @Test
    public void editPermissions2() throws InactiveTeamException, UnauthorizedPageOwnerException, UnauthorizedTeamOwnerException, PersonalPageYetToBeCreatedException, UnauthorizedTeamManagerException, MemberIsAlreadyTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, UserIsNotThisKindOfMemberException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        owner.assignNewTeamManager(futureManager,200000);
        futureManager = system.getMemberInstanceByKind(futureManager.getName(),"Team Manager");
        ((TeamManager)futureManager).editPermissions(owner,"Create Personal Page",true);
        assertTrue(((TeamManager)futureManager).getPermissions().get("Create Personal Page"));
    }
    @Test
    public void hireCoach() throws UserInformationException, InactiveTeamException, UnauthorizedTeamManagerException, UnauthorizedPageOwnerException, UnauthorizedTeamOwnerException, PersonalPageYetToBeCreatedException, MemberIsAlreadyTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, UserIsNotThisKindOfMemberException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        owner2.assignNewTeamManager(futureManager2,300000);
        futureManager2 = system.getMemberInstanceByKind(futureManager2.getName(),"Team Manager");
        ((TeamManager)futureManager2).editPermissions(owner2,"Hire Coach",true);
        assertTrue(((TeamManager)futureManager2).getPermissions().get("Hire Coach"));
        IAsset newCoach = new Coach("newCoach","ahmad",1000,"234235",68,null,CoachRole.FitnessCoach);
        ((TeamManager)futureManager2).hireCoach(newCoach);
        assertTrue(((TeamManager)futureManager2).getMyTeam().getTeamCoaches().containsValue(newCoach));

    }


    @Test
    public void createPersonalPageForTeam() throws UnauthorizedPageOwnerException, InactiveTeamException, UnauthorizedTeamManagerException, UserInformationException, UnauthorizedTeamOwnerException, PersonalPageYetToBeCreatedException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, UserIsNotThisKindOfMemberException, MemberIsAlreadyTeamManagerException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        owner3.assignNewTeamManager(futureManager3,1222);
        futureManager3 = system.getMemberInstanceByKind(futureManager3.getName(),"Team Manager");
        ((TeamManager)futureManager3).editPermissions(owner3,"Create Personal Page",true);
        assertTrue(((TeamManager)futureManager3).getPermissions().get("Create Personal Page"));
        boolean ans = ((TeamManager)futureManager3).createPersonalPageForTeam();
        assertTrue(ans);
    }

    @Test
    public void addContentToTeamsPersonalPage() throws UnauthorizedPageOwnerException, InactiveTeamException, UnauthorizedTeamManagerException {
        boolean ans = teamManagerTest.addContentToTeamsPersonalPage(content);
        assertFalse(ans);

    }

    @Test
    public void editProfileOnPersonalPage() throws UnauthorizedTeamManagerException, InactiveTeamException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamOwnerException, TeamOwnerWithNoTeamException, UserInformationException, UserIsNotThisKindOfMemberException, MemberIsAlreadyTeamManagerException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        owner4.assignNewTeamManager(futureManager4,5);
        futureManager4 = system.getMemberInstanceByKind(futureManager4.getName(),"Team Manager");
        ((TeamManager)futureManager4).editPermissions(owner4,"Create Personal Page",true);
        assertTrue(((TeamManager)futureManager4).getPermissions().get("Create Personal Page"));
        ((TeamManager)futureManager4).createPersonalPageForTeam();
        APersonalPageContent content = new ProfileContent();
        ((TeamManager)futureManager4).editPermissions(owner4,"Edit Personal Page Profile",true);
        ((TeamManager)futureManager4).editPermissions(owner4,"Add Content To Personal Page",true);
        ((TeamManager)futureManager4).addContentToTeamsPersonalPage(content);
        boolean ans = ((TeamManager)futureManager4).editProfileOnPersonalPage("height", "1.80");
        assertTrue(ans);
    }

    @Test
    public void isAutorizedTeamOwner() throws UnauthorizedTeamOwnerException {
        teamManagerTest.setTeamOwnerAssignedThis(owner);
        boolean ans = teamManagerTest.isAutorizedTeamOwner(owner);
        assertTrue(ans);
    }

    @Test
    public void getMyTeam() {
    }

    @Test
    public void setTeamOwnerAssignedThis() throws UnauthorizedTeamOwnerException {
        teamManagerTest.setTeamOwnerAssignedThis(owner);
        assertTrue(teamManagerTest.isAutorizedTeamOwner(owner));
    }

    @Test
    public void getAssetID() {
        teamManagerTest.getAssetID();
    }
    @Test
    public void resetPermissions(){
        teamManagerTest.resetPermissions();
        HashMap<String,Boolean> result = new HashMap<>();
        result=teamManagerTest.getPermissions();
        for(String s : result.keySet()){
            assertEquals(false,result.get(s));
        }
    }

    @Test
    //+getValue
    public void edit() {
        teamManagerTest.edit(5);
        assertEquals(5, teamManagerTest.getValue());
    }
}
