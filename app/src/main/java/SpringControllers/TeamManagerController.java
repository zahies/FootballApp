package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UsersDAL.TeamManagerDAL;
import Domain.PersonalPages.APersonalPageContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.Users.TeamManager;
import FootballExceptions.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.SQLException;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TeamManagerController extends MemberController {

    private TeamManager teamManager;

    public TeamManagerController(TeamManager teamManager) {
        this.teamManager = teamManager;
    }


    /**
     * all Func in this controller are all according to UC 7.1 - team manager can do what team owner let him
     */

    public boolean hireCoach(String username, IAsset newCoach) {
        try {
            TeamManager teamManager = (TeamManager) new TeamManagerDAL().select(username);
            return teamManager.hireCoach(newCoach);
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean hireCoach(IAsset newCoach) {
        try {
            return teamManager.hireCoach(newCoach);
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean createPersonalPageForTeam(String username) {
        try {
            TeamManager teamManager = (TeamManager) new TeamManagerDAL().select(username);
            return teamManager.createPersonalPageForTeam();
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
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
        }
        return false;
    }



    public boolean createPersonalPageForTeam() { //fixme
        try {
            return teamManager.createPersonalPageForTeam();
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addContentToTeamsPersonalPage(String username, APersonalPageContent content) {
        try {
            TeamManager teamManager = (TeamManager) new TeamManagerDAL().select(username);
            return teamManager.addContentToTeamsPersonalPage(content);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
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
        }
        return false;
    }
    public boolean addContentToTeamsPersonalPage(APersonalPageContent content) { //fixme
        try {
            return teamManager.addContentToTeamsPersonalPage(content);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean editProfileOnPersonalPage(String username, String title, String val) {
        try {
            TeamManager teamManager = (TeamManager) new TeamManagerDAL().select(username);
            return teamManager.editProfileOnPersonalPage(title, val);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean editProfileOnPersonalPage(String title, String val) { //fixme
        try {
            return teamManager.editProfileOnPersonalPage(title, val);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
