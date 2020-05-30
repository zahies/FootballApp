package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.ComplaintFormsDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UsersDAL.SystemManagerDAL;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Team;
import Domain.SystemLog;
import Domain.Users.Member;
import Domain.Users.SystemManager;
import FootballExceptions.*;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.List;

public class SystemManagerController extends MemberController {

    public boolean deleteMember(String systemManagerusername, String memberusername) {
        boolean flag = false;
        try {
            SystemManager systemManager = (SystemManager)new SystemManagerDAL().select(systemManagerusername,true);
            Member member = (Member)new SystemManagerDAL().select(memberusername,true);
            systemManager.deleteMember(member);
            flag=true;
        } catch (UnableToRemoveException e) {
            System.out.println("Member is the Only team Owner");///retrun string value maybe?!
        } catch (NoPermissionException e) {
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
        return flag;
    }

    public boolean closeTeam(String systemManagerusername, String teamstring, String reason) {
        boolean flag = false;
        try {
            SystemManager systemManager = (SystemManager)new SystemManagerDAL().select(systemManagerusername,true);
            Team team = (Team) new TeamsDAL().select(teamstring,true);
            systemManager.closeTeam(team, reason);
            flag=true;
        } catch (InactiveTeamException e) {
            System.out.println("Team is allready closed");// maybeString
        } catch (UnableToRemoveException e) {
            System.out.println(e.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

        return flag;

    }

    public String showLog(String systemManagerusername) throws IOException {
        try {
            SystemManager systemManager = (SystemManager) new SystemManagerDAL().select(systemManagerusername,true);
            return systemManager.getLog();
        } catch (UserIsNotThisKindOfMemberException e) {

        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<ComplaintForm> checkComplaints(String systemManagerusername) {
        try {
            SystemManager systemManager = (SystemManager) new SystemManagerDAL().select(systemManagerusername,true);
            return systemManager.checkComplaints();

        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean CommentOnComplaint(String systemManagerusername, String compstring, String response) {
        boolean flag=false;
        try {
            SystemManager sm = (SystemManager) new SystemManagerDAL().select(systemManagerusername,true);
            ComplaintForm comp = (ComplaintForm) new ComplaintFormsDAL().select(compstring,true);
            sm.CommentOnComplaint(comp, response);
            flag=true;
        } catch (ShortCommentException e) {
            System.out.println(e.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

    return flag;

    }


}
