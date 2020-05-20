package SpringControllers;

import Domain.Searcher.Searcher;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.Fan;
import Domain.Users.PersonalInfo;
import FootballExceptions.AlreadyFollowThisPageException;
import FootballExceptions.UserInformationException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class FanController {

    public boolean addPersonalPagesToFollow(Fan fan, List<PersonalInfo> pagesToFollow) {
        try {
            fan.addPersonalPagesToFollow(pagesToFollow);
        } catch (AlreadyFollowThisPageException e) {
            return false;
        }
        return true;
    }

    public void unFollowPage(Fan fan, PersonalInfo page) {
        fan.unFollowPage(page);

    }


    public boolean turnAlertForPersonalPageOn(Fan fan, PersonalInfo page) {
        return fan.turnAlertForPersonalPageOn(page);
    }


    public void submitComplaintForm(Fan fan, ComplaintForm complaintForm) {
        fan.submitComplaintForm(complaintForm);
    }


    public LinkedList<String> viewSearchHistory(Fan fan) {
        if (fan.viewSearchHistory().size() == 0) {
            System.out.println("no search history yet");
            return null;
        } else
            return fan.viewSearchHistory();
    }


    public ArrayList<Pair<String, String>> viewPersonalDetails(Fan fan) {

        return fan.viewPersonalDetails();
    }


    public boolean changePassword(Fan fan, String newPassword) {

        try {
            return fan.changePassword(newPassword);
        } catch (UserInformationException e) {
            System.out.println("wrong user name");
            return false;
        }
    }

    public boolean changeUserName(Fan fan, String newUserName) {
        try {
            return fan.changeUserName(newUserName);
        } catch (UserInformationException e) {
            System.out.println("wrong user name");
            return false;
        }

    }

    public double useReccommandationSystem(Fan fan, Game game, Team team) {
        return fan.useRecommandationSystem(game, team);
    }


    public HashSet<Object> search(Fan fan, String str, Searcher searcher) {
        return fan.search(str, searcher);

    }


}
