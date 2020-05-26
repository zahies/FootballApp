package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.SeasonManagment.BudgetActivity;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.TeamStatus;
import Domain.Users.Member;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.Date;

public class TeamOwnerController extends MemberController {

    /**
     * UC 6.1 - adding asset to team
     */
    public boolean addAssetToTeam(TeamOwner teamOwner, IAsset asset) {
        try {
            teamOwner.addAssetToTeam(asset);
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * UC 6.1 - removing asset from team
     */
    public boolean removeAssetFromTeam(TeamOwner teamOwner, IAsset asset) {
        try {
            return teamOwner.removeAssetFromTeam(asset);
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTeamAssetException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * UC 6.1 - editing team asset
     */
    public boolean editAsset(TeamOwner teamOwner, IAsset asset, int value) {
        try {
            return teamOwner.editAsset(asset, value);
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTeamAssetException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * UC 6.2 - assign member to become team owner
     */
    public boolean assignNewTeamOwner(TeamOwner teamOwner, Member member) {
        try {
            return teamOwner.assignNewTeamOwner(member);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (MemberIsAlreadyTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (MemberIsAlreadyTeamManagerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * UC 6.3 - remove team owner
     */
    public boolean removeTeamOwner(TeamOwner teamOwner, TeamOwner member) {
        try {
            return teamOwner.removeTeamOwner(member);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (CantRemoveMainOwnerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * UC 6.4 - assign a new team manager
     */
    public boolean assignNewTeamManager(TeamOwner teamOwner, Member member, int value) {
        try {
            return teamOwner.assignNewTeamManager(member, value);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (MemberIsAlreadyTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (MemberIsAlreadyTeamManagerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * UC 6.5 - remove team manager
     */
    public boolean removeTeamManager(TeamOwner teamOwner, TeamManager teamManager) throws UserIsNotThisKindOfMemberException {
        try {
            return teamOwner.removeTeamManager(teamManager);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * UC 6.6 - change team status
     */
    public boolean changeTeamStatus(TeamOwner teamOwner, TeamStatus teamStatus) {
        try {
            return teamOwner.changeTeamStatus(teamStatus);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (TeamCannotBeReopenException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * UC 6.7 - add budget activity
     */
    public boolean addBudgetActivity(TeamOwner teamOwner, Date date, BudgetActivity budgetActivity, int amount) {
        try {
            return teamOwner.addBudgetActivity(date, budgetActivity, amount);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean sendRegisterRequestForNewTeam(String username, String teamName, int leaugueID, int year){
        boolean succeeded = false;
//        try {
//            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(username);
//            Leaugue league = new LeaugueDAL().select(leaugueID);
//            teamOwner.sendRegisterRequestForNewTeam(teamName,league,year);
//            succeeded = true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        }
        return succeeded;
    }
}
