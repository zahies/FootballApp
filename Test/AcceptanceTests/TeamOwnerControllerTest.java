package AcceptanceTests;

import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.BudgetActivity;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;
import Domain.Users.Member;
import Domain.Users.Player;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;
import FootballExceptions.TeamOwnerWithNoTeamException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import Service.TeamOwnerController;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TeamOwnerControllerTest {
    private TeamOwnerController ownerController;
    private IAsset iAsset,iAsset2,iAsset3;
    private Team team,team2,team4,team5;
    private TeamOwner owner,owner2,owner3,owner4,owner5;
    private Member futureOwner,futureOwner2;
    private Member futureManager,futureManager2;
    private TeamManager teamManager;
    @Before
    public void init(){
        ownerController = new TeamOwnerController();
        owner = new TeamOwner("Ali","ali",1005,"32432");
        owner2 = new TeamOwner("Yoav","yoav",1006,"32432");
        owner3 = new TeamOwner("Ben","ben",1009,"32432");
        owner4 = new TeamOwner("Ben1","ben1",1009,"32432");
        owner5 = new TeamOwner("Ben2","ben2",1009,"32432");
        iAsset = new Player("Shay","shay",1004,"3434",1004,null,null);
        iAsset2 = new Player("Shay1","shay",1004,"3434",1004,null,null);
        iAsset3 = new Player("Shay2","shay",1004,"3434",1004,null,null);
        team= new Team("bestTeamEver",owner);
        team2= new Team("bestTeamEver2",owner2);
        team4= new Team("bestTeamEver3",owner4);
        team5= new Team("bestTeamEver5",owner5);
        futureOwner = new Player("Shir","shir",1007,"24",1007,null,null);
        futureOwner2 = new Player("Shira","shira",1008,"24",1008,null,null);
        teamManager = new TeamManager("noa","moa",1014,"3242",1014,null,null);
        futureManager = new Player("Itamar","itamar",2424,"#543",1013,null,null);
        futureManager2 = new Player("Zach","zachi",1015,"#543",1015,null,null);
        //futureOwner2 = new Player("Shi","shi",1012,"24",1008,null,null);
    }

    @Test
    public void addAssetToTeamPositive() {
        ownerController.addAssetToTeam(owner,iAsset);
        assertNotNull(team.getTeamPlayers().values().contains(iAsset));
    }

    @Test
    public void addAssetToTeamNegative() {
        ownerController.addAssetToTeam(owner2,iAsset);

    }

    @Test
    public void assignNewTeamOwnerPositive() throws UserIsNotThisKindOfMemberException {
        ownerController.assignNewTeamOwner(owner, futureOwner);
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        futureOwner = system.getMemberInstanceByKind(futureOwner.getName(),"Team Owner");
        assertTrue(team.getAllTeamOwners().contains(futureOwner));
        
    }

    @Test
    public void assignNewTeamOwnerNegative() throws UserIsNotThisKindOfMemberException {
        assertFalse( ownerController.assignNewTeamOwner(owner3, futureOwner2));
    }

    @Test
    public void removeTeamOwnerPositive() throws UserIsNotThisKindOfMemberException {

        ownerController.assignNewTeamOwner(owner2, futureOwner2);
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        futureOwner2 = system.getMemberInstanceByKind(futureOwner2.getName(),"Team Owner");
        assertTrue(ownerController.removeTeamOwner(owner2,(TeamOwner)futureOwner2));


    }
    @Test
    public void removeTeamOwnerNegative() throws UserIsNotThisKindOfMemberException {

        assertFalse(ownerController.removeTeamOwner(owner3,owner));
    }

    @Test
    public void assignNewTeamManagerPositive() throws UserIsNotThisKindOfMemberException {
        ownerController.assignNewTeamManager(owner, futureManager,1007);
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        futureManager = system.getMemberInstanceByKind(futureManager.getName(),"Team Manager");
        assertTrue(team.getAllTeamManaers().contains(futureManager));
    }

    @Test
    public void assignNewTeamManagerNegative() throws UserIsNotThisKindOfMemberException {
        assertFalse( ownerController.assignNewTeamManager(owner3, futureManager,1010));
    }

    @Test
    public void removeTeamManagerPositive() throws UserIsNotThisKindOfMemberException {
        ownerController.assignNewTeamManager(owner2, futureManager2,1008);
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        futureManager2 = system.getMemberInstanceByKind(futureManager2.getName(),"Team Manager");
        assertTrue(ownerController.removeTeamManager(owner2,(TeamManager)futureManager2));
    }

    @Test
    public void removeTeamManagerNegative() throws UserIsNotThisKindOfMemberException {
        assertFalse(ownerController.removeTeamManager(owner3, teamManager));
    }

    @Test
    //closeTeam
    public void changeTeamStatusPositive() {
        ownerController.changeTeamStatus(owner4, TeamStatus.Close);
        assertEquals(TeamStatus.Close,team4.getStatus());
    }

    @Test
    //closeTeam
    public void changeTeamStatusNegative() {
        team5.setStatus(TeamStatus.Close);
        assertFalse(ownerController.changeTeamStatus(owner5, TeamStatus.Close));
    }

    @Test
    //closeTeam
    public void changeTeamStatusPositiveOpenTeam() {
        TeamOwner owner6 = new TeamOwner("Ben3","ben3",1009,"32432");
        Team team6 = new Team("noaWeiss",owner6);
        ownerController.changeTeamStatus(owner6, TeamStatus.Active);
        assertEquals(TeamStatus.Active,team6.getStatus());
    }

    @Test
    //openTeam
    public void changeTeamStatusNegativeOpenTeam() {
        TeamOwner owner6 = new TeamOwner("Ben3","ben3",1009,"32432");
        Team team6 = new Team("noaWeiss",owner6);
        team5.setStatus(TeamStatus.Active);
        assertFalse(ownerController.changeTeamStatus(owner5, TeamStatus.Active));
    }

    @Test
    public void addBudgetActivityPositive() {
        TeamOwner owner6 = new TeamOwner("Ben3","ben3",1009,"32432");
        Team team6 = new Team("noaWeiss",owner6);
        Date date = new Date("30/09/2020");
        assertTrue(ownerController.addBudgetActivity(owner6,date, BudgetActivity.BuyPlayer,70));
    }

    @Test
    public void addBudgetActivityNegative() {
        TeamOwner owner6 = new TeamOwner("Ben3","ben3",1009,"32432");
        Team team6 = new Team("noaWeiss",owner6);
        team6.setStatus(TeamStatus.Close);
        Date date = new Date("30/09/2020");
        assertFalse(ownerController.addBudgetActivity(owner6,date, BudgetActivity.BuyPlayer,70));
    }

    @Test
    public void editAssetPositive(){
        TeamOwner owner6 = new TeamOwner("Ben3","ben3",1009,"32432");
        Team team6 = new Team("noaWeiss",owner6);
        ownerController.addAssetToTeam(owner6,iAsset2);
        assertTrue(ownerController.editAsset(owner6,iAsset2,123));

    }

    @Test
    public void editAssetNegative(){
        TeamOwner owner6 = new TeamOwner("Ben3","ben3",1009,"32432");
        Team team6 = new Team("noaWeiss",owner6);
        assertFalse(ownerController.editAsset(owner6,iAsset3,123));
    }
}