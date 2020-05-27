package Domain.Users;

import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.*;
import FootballExceptions.*;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.*;

public class TeamOwnerTest {
    private TeamOwner ownerTest;
    private TeamOwner ownerTestWithNoTeam;
    private Team team;
    private IAsset iAsset;
    private Member futureOwner;
    private Member futureManager;
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    @Before
    public void init(){
        futureOwner = new Fan("tzach","Moti Lohim", 1654654, "SDA");
        futureManager = new Fan("moshe","Moti Lohim", 1654654, "SDA");
        ownerTest = new TeamOwner("Avi","Avi",2424,"234");
        team = new Team("Maccabi",ownerTest);
        ownerTestWithNoTeam = new TeamOwner("Yanki","Avi",2424,"234");
        iAsset = new Player("Dani","Dani",24,"3434",4,null,null);
    }


    @Test(expected = TeamOwnerWithNoTeamException.class)
    public void addAssetToTeam() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        ownerTestWithNoTeam.addAssetToTeam(iAsset);

    }

    @Test
    public void addAssetToTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        boolean ans = ownerTest.addAssetToTeam(iAsset);
        assertTrue(ans);
    }
    @Test
    public void addCoachToTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        boolean ans = ownerTest.addAssetToTeam(new Coach("jesus","dasd" ,32432,"sds'",14984,null,CoachRole.HeadCoach));
        assertTrue(ans);
    }
    @Test
    public void addFieldToTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        boolean ans = ownerTest.addAssetToTeam(new Field());
        assertTrue(ans);
    }
    @Test(expected = InactiveTeamException.class)
    public void addAssetToTeamWhenTeamIsClosed() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, TeamCannotBeReopenException {
        ownerTest.changeTeamStatus(TeamStatus.Close);
        ownerTest.addAssetToTeam(iAsset);
    }

    @Test
    public void editAsset() throws InactiveTeamException, InvalidTeamAssetException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException {
        ownerTest.addAssetToTeam(iAsset);
        ownerTest.editAsset(iAsset,4198);
        assertEquals(4198,iAsset.getValue());
    }

    @Test(expected = TeamOwnerWithNoTeamException.class)
    public void editAsset2() throws InactiveTeamException, InvalidTeamAssetException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException {
        ownerTestWithNoTeam.editAsset(iAsset,4198);
    }

    @Test
    public void removeAssetFromTeam() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        addAssetToTeam2();
        ownerTest.setTeam(null);
        boolean ans = false;
        try {
            ans = ownerTest.removeAssetFromTeam(iAsset);
        } catch (InactiveTeamException e) {
            e.printStackTrace();
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            e.printStackTrace();
        } catch (InvalidTeamAssetException e) {
            e.printStackTrace();
        }
        assertFalse(ans);


    }

    @Test
    public void removeAssetFromTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        addAssetToTeam2();
        ownerTest.removeAssetFromTeam(iAsset);
        assertEquals(0,ownerTest.getTeam().getTeamPlayers().size());
    }

    @Test(expected = InactiveTeamException.class)
    public void removeAssetFromTeam2WhenInactiveTeam() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException, TeamCannotBeReopenException {
        addAssetToTeam2();
        ownerTest.changeTeamStatus(TeamStatus.Close);
        ownerTest.removeAssetFromTeam(iAsset);
        assertEquals(0,ownerTest.getTeam().getTeamPlayers().size());
    }
    @Test(expected = InvalidTeamAssetException.class)
    public void removeInvalidAssetFromTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        ownerTest.removeAssetFromTeam(iAsset);
    }

    @Test
    public void removeCoachFromTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        IAsset coach = new Coach("jesus","dasd" ,32432,"sds'",14984,null,CoachRole.HeadCoach);

        ownerTest.addAssetToTeam(coach);
        ownerTest.removeAssetFromTeam(coach);
        assertEquals(0,ownerTest.getTeam().getTeamPlayers().size());
    }

    @Test(expected = InvalidTeamAssetException.class)
    public void removeInvalidCoachFromTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        ownerTest.removeAssetFromTeam(iAsset);
    }

    @Test
    public void removeFieldFromTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        IAsset field = new Field();
        ownerTest.addAssetToTeam(field);
        ownerTest.removeAssetFromTeam(field);
        assertEquals(0,ownerTest.getTeam().getTeamPlayers().size());
    }

    @Test(expected = InvalidTeamAssetException.class)
    public void removeInvalidFieldFromTeam2() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        ownerTest.removeAssetFromTeam(iAsset);
        assertEquals(0,ownerTest.getTeam().getTeamPlayers().size());
    }

    @Test
    public void assignNewTeamOwner() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException {
    //    futureManager = new Fan("tzach","Moti Lohim", 1654654, "SDA");
        TeamOwner teamOwnerAssign = new TeamOwner("Shlomo32523","Shlo sadmo",5235,"$124");
        Team team1 = new Team("L$@#",teamOwnerAssign);
        Player player = new Player("Trex","Ffsd",4124,"4214",213,"gwegew",null);
        teamOwnerAssign.assignNewTeamOwner(player);

        assertTrue(team1.isTeamOwner(player));
    }
    @Test (expected = TeamOwnerWithNoTeamException.class)
    public void assignNewTeamOwner2() throws InactiveTeamException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, UserInformationException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException {

        ownerTestWithNoTeam.assignNewTeamOwner(futureOwner);

        //assertTrue(team.isTeamOwner(futureOwner));
    }

    @Test
    public void removeTeamOwner() throws UserIsNotThisKindOfMemberException, TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, CantRemoveMainOwnerException {
//        ownerTest.assignNewTeamOwner(futureOwner);
        futureOwner= system.getMemberInstanceByKind(futureOwner.getName(),"Team Owner");
        ownerTest.removeTeamOwner((TeamOwner) futureOwner);
        assertFalse(team.isTeamOwner(futureOwner));
    }

    @Test(expected = CantRemoveMainOwnerException.class)
    public void removeMainOwner() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UserIsNotThisKindOfMemberException, CantRemoveMainOwnerException {
        TeamOwner MainTeamOwner = new TeamOwner("Main","mainowner",214,"DAS");
        Team teamm= new Team("invincibles",MainTeamOwner);
        Member secOwner = new Fan("Henry", "fsag",2141,"353255");
        MainTeamOwner.assignNewTeamOwner(secOwner);
        secOwner = ((TeamOwner)system.getMemberInstanceByKind(secOwner.getName(),"Team Owner"));
        ((TeamOwner)secOwner).removeTeamOwner(MainTeamOwner);
    }

    @Test(expected = TeamOwnerWithNoTeamException.class)
    public void removeTeamOwner2() throws UserIsNotThisKindOfMemberException, TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, CantRemoveMainOwnerException {
        ownerTest.assignNewTeamOwner(futureOwner);
        futureOwner= system.getMemberInstanceByKind(futureOwner.getName(),"Team Owner");
        ownerTestWithNoTeam.removeTeamOwner((TeamOwner) futureOwner);
        assertFalse(team.isTeamOwner(futureOwner));
    }

    @Test
    public void assignNewTeamManger() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException {
        ownerTest.assignNewTeamManager(futureManager,4234);
        assertTrue(team.isTeamManager(futureManager));
    }

    @Test(expected = MemberIsAlreadyTeamManagerException.class)
    public void assignNewTeamManger2() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException {
        ownerTest.assignNewTeamManager(futureManager,4234);
        ownerTest.assignNewTeamManager(futureManager,4234);

    }

    @Test
    public void editManagerPermissions() throws UserIsNotThisKindOfMemberException {
        init();
        Member member = new Coach("Edu","Eduardo Silva",33123,"21424",25894,"sdas",CoachRole.HeadCoach);
        try {
            ownerTest.setTeam(team);
     //       team.setOwner(ownerTest);
            ownerTest.assignNewTeamManager(member,((Coach)member).getValue());
            ownerTest.editManagerPermissions(member,"Hire Coach",true);
        } catch (MemberIsAlreadyTeamOwnerException e) {
            e.printStackTrace();
        } catch (MemberIsAlreadyTeamManagerException e) {
            e.printStackTrace();
        } catch (TeamOwnerWithNoTeamException e) {
            e.printStackTrace();
        } catch (UnauthorizedTeamOwnerException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            e.printStackTrace();
        } catch (UnauthorizedPageOwnerException e) {
            e.printStackTrace();
        } catch (PersonalPageYetToBeCreatedException e) {
            e.printStackTrace();
        }
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        Member newTeamManager = system.getMemberInstanceByKind(member.getName(),"Team Manager");
        HashMap <String,Boolean> permissions = ((TeamManager)newTeamManager).getPermissions();
        assertEquals(true,permissions.get("Hire Coach"));
    }

    @Test
    public void removeTeamManage() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UserIsNotThisKindOfMemberException {
        //ownerTest.assignNewTeamManager(futureManager,4234);
        futureManager=system.getMemberInstanceByKind(futureManager.getName(),"Team Manager");
        ownerTest.removeTeamManager((TeamManager)futureManager);
        assertFalse(team.isTeamManager(futureManager));
    }

    @Test
    public void changeStatus() throws TeamCannotBeReopenException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException {
        ownerTest.changeTeamStatus(TeamStatus.Close);
        assertEquals(TeamStatus.Close,ownerTest.getTeam().getStatus());
    }

    @Test
    public void addBudgetActivity() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        ownerTest.addBudgetActivity(new Date(2020,02,03), BudgetActivity.SellPlayer,400);
        assertTrue(ownerTest.getTeam().getControlBudget().findQuarter(new Date(2020,02,03)).getFinanceActivity().contains(new Pair<>(BudgetActivity.SellPlayer,400)));
    }

    @Test
    public void addBugetActivityOver() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException {
        TeamOwner teamOwnerwithMoreIncome = new TeamOwner("Yossi23653457","Yossi Benayoun", 248765,"fs@!#'", UUID.randomUUID());
        Team teamB = new Team("Shoko",teamOwnerwithMoreIncome);
        teamOwnerwithMoreIncome.addBudgetActivity(new Date(2020,04,03), BudgetActivity.BuyPlayer,400);
        teamOwnerwithMoreIncome.addBudgetActivity(new Date(2020,04,03), BudgetActivity.Salaries,400);
        teamOwnerwithMoreIncome.addBudgetActivity(new Date(2020,04,03), BudgetActivity.MaintenanceField,400);
        teamOwnerwithMoreIncome.addBudgetActivity(new Date(2020,04,03), BudgetActivity.Tax,400);
        teamOwnerwithMoreIncome.addBudgetActivity(new Date(2020,04,03), BudgetActivity.GameIncome,1500);
        Member comi = new Commissioner("Revivowqeqw",3424234,"Haim Revivo", "sda@!#");
        FootballManagmentSystem.getInstance().addMember(comi);
        comi.logOut();
        teamB.getControlBudget().checkIncomeBiggerThanOutcome();
        assertEquals(1,comi.getAlertsList().size());

    }
}