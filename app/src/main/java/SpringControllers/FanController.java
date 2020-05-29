package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.ComplaintFormsDAL;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import DataAccess.UsersDAL.CommissionersDAL;
import DataAccess.UsersDAL.FansDAL;
import DataAccess.UsersDAL.PersonalInfoDAL;
import Domain.Searcher.Searcher;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.Commissioner;
import Domain.Users.Fan;
import Domain.Users.PersonalInfo;
import FootballExceptions.AlreadyFollowThisPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class FanController {

    public boolean addPersonalPagesToFollow(String username, List<PersonalInfo> pagesToFollow) {
        boolean flag = false;
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            fan.addPersonalPagesToFollow(pagesToFollow);
            flag = true;
        } catch (AlreadyFollowThisPageException e) {
            return false;
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

    public boolean unFollowPage(String username,  String pagestring) {
        boolean flag = false;
        try {
            Fan fan = (Fan) new FansDAL().select(username);
             PersonalInfo page = (PersonalInfo) new PersonalInfoDAL().select(pagestring);
            fan.unFollowPage(page);
            flag = true;
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


    public boolean turnAlertForPersonalPageOn(String username,  String pagestring) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            PersonalInfo page = (PersonalInfo) new PersonalInfoDAL().select(pagestring);
            return fan.turnAlertForPersonalPageOn(page);
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
        return false;
    }

    public boolean submitComplaintForm(String username, String complaintFormstring) {
        boolean flag = false;
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            ComplaintForm complaintForm = (ComplaintForm) new ComplaintFormsDAL().select(complaintFormstring);
            fan.submitComplaintForm(complaintForm);
            flag = true;
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



    public LinkedList<String> viewSearchHistory(String username) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            if (fan.viewSearchHistory().size() == 0) {
                System.out.println("no search history yet");
                return null;
            } else
                return fan.viewSearchHistory();
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


    public ArrayList<Pair<String, String>> viewPersonalDetails(String username) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            return fan.viewPersonalDetails();
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


    public boolean changePassword(String username, String newPassword) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            return fan.changePassword(newPassword);
        } catch (UserInformationException e) {
            System.out.println("wrong user name");
            return false;
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeUserName(String username, String newUserName) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            return fan.changeUserName(newUserName);
        } catch (UserInformationException e) {
            System.out.println("wrong user name");
            return false;
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }



    public double useReccommandationSystem(String username, String gamestr, String teamstr) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            Game game = (Game) new GamesDAL().select(gamestr);
            Team team = (Team) new TeamsDAL().select(teamstr);

            return fan.useRecommandationSystem(game, team);
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
        return -999999999; //fixme
    }








    public HashSet<Object> search(String username, String str, Searcher searcher) {
        try {
            Fan fan = (Fan) new FansDAL().select(username);
            return fan.search(str, searcher);
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


}
