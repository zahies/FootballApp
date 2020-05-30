package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UsersDAL.MembersDAL;
import DataAccess.UsersDAL.TeamManagerDAL;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.SeasonManagment.*;
import Domain.Users.*;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TeamOwnerController extends MemberController {

    /**
     * UC 6.1 - adding asset to team
     */
    public boolean addAssetToTeam(String username, IAsset asset) {
        try {
            TeamOwner teamOwner = new TeamOwnersDAL().select(username,true);
            teamOwner.addAssetToTeam(asset);
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * UC 6.1 - removing asset from team
     */
    public boolean removeAssetFromTeam(String username, IAsset asset) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(username,true);
            return teamOwner.removeAssetFromTeam(asset);
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTeamAssetException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * UC 6.1 - editing team asset
     */
    public boolean editAsset(String username, IAsset asset, int value) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(username,true);
            return teamOwner.editAsset(asset, value);
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTeamAssetException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }



    public Map<String, LinkedList<String>> getMyteam(String username) throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        HashMap<String,LinkedList<String>> toreturn = new HashMap<>();
        TeamOwner owner = new TeamOwnersDAL().select(username,true);
        Team teamHome = owner.getTeam();
        LinkedList<String> players = new LinkedList<>();
        for (IAsset ass:teamHome.getTeamPlayers().values()
        ) {
            Player p = (Player)ass;
            players.add(p.getName());
        }
        LinkedList<String> coaches = new LinkedList<>();
        for (IAsset ass:teamHome.getTeamCoaches().values()
        ) {
            Coach p = (Coach) ass;
            coaches.add(p.getName());
        }

        toreturn.put("players",players);
        toreturn.put("coaches",coaches);
        return toreturn;
    }

    /**
     * UC 6.2 - assign member to become team owner
     */
    public boolean assignNewTeamOwner(String username, Member member) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(username,true);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * UC 6.3 - remove team owner
     */
    public boolean removeTeamOwner(String teamOwnerstring, String memberstring) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(teamOwnerstring,true);
            TeamOwner member = (TeamOwner) new TeamOwnersDAL().select(memberstring,true);
            return teamOwner.removeTeamOwner(member);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (CantRemoveMainOwnerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * UC 6.4 - assign a new team manager
     */
    public boolean assignNewTeamManager(String teamOwnerstring, String memberstring, int value) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(teamOwnerstring,true);
            TeamOwner member = (TeamOwner) new TeamOwnersDAL().select(memberstring,true);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }






    /**
     * UC 6.5 - remove team manager
     */
    public boolean removeTeamManager(String teamOwnerstring, String teamManagerstring) throws UserIsNotThisKindOfMemberException {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(teamOwnerstring,true);
            TeamManager teamManager = (TeamManager) new TeamManagerDAL().select(teamManagerstring,true);
            return teamOwner.removeTeamManager(teamManager);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * UC 6.6 - change team status
     */
    public boolean changeTeamStatus(String teamOwnerstring, TeamStatus teamStatus) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(teamOwnerstring,true);
            return teamOwner.changeTeamStatus(teamStatus);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (TeamCannotBeReopenException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException | NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }





    /**
     * UC 6.7 - add budget activity
     */
    public boolean addBudgetActivity(String teamOwnerstring, Date date, BudgetActivity budgetActivity, int amount) {
        try {
            TeamOwner teamOwner = (TeamOwner) new TeamOwnersDAL().select(teamOwnerstring,true);
            return teamOwner.addBudgetActivity(date, budgetActivity, amount);
        } catch (TeamOwnerWithNoTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamOwnerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
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
