package Domain.Users;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Alerts.IAlert;
import Domain.FootballManagmentSystem;
import Domain.Searcher.Searcher;
import Domain.SeasonManagment.*;
import FootballExceptions.AlreadyFollowThisPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.*;

public class Fan extends Member implements Observer {
    private HashMap<PersonalInfo, Boolean> personalPagesFollowed; //Tracking personal pages, boolean represent alerts on/off
    private LinkedList<String> searchHistory = new LinkedList<>();
    private FootballManagmentSystem system;
    private RecommendationSystem recommendationSystem;


    public Fan(String name, String realname, int id, String password) {
        super(name, id, password, realname);
        searchHistory = new LinkedList<>();
        system = FootballManagmentSystem.getInstance();
        recommendationSystem = new RecommendationSystem();
        personalPagesFollowed = new HashMap<>();
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * update function when fan gets alerts from game for game event or from page for page changed alert
     *
     * @param o   - object created the alert
     * @param arg - alert
     */
    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof Game) {
            if (personalPagesFollowed.containsKey(((Game) o).getHome().getInfo())) {
                if (personalPagesFollowed.get(((Game) o).getHome().getInfo())) { /// handle alert only if this fan has alerts on for home team
                    handleAlert((IAlert) arg);
                    return;
                }
            }
            if (personalPagesFollowed.containsKey(((Game) o).getAway().getInfo())) { // Same thing just for away team
                if (personalPagesFollowed.get(((Game) o).getAway().getInfo())) {
                    handleAlert((IAlert) arg);
                    return;
                }
            }
        }

        if (o instanceof PersonalInfo) {
            if (personalPagesFollowed.containsKey(o)) { // handle alert only if this fan follows this page
                handleAlert((IAlert) arg);
                return;
            }
        }
    }

    /**
     * UC - 3.2
     */

    /**
     * 2 fucns for UC - 3.2: one to follow and the other to unfollow
     */
    public void addPersonalPagesToFollow(List<PersonalInfo> pagesToFollow) throws AlreadyFollowThisPageException {
        for (PersonalInfo page : pagesToFollow) {
            if (personalPagesFollowed.containsKey(page)) {
                throw new AlreadyFollowThisPageException();
            }
            page.addFollower(this);
            personalPagesFollowed.put(page, false); //By default alerts are of
        }
    }

    public void unFollowPage(PersonalInfo page) {
        page.removeFollower(this);
        personalPagesFollowed.remove(page);

    }

    /**
     * UC 3.3 - turning alerts on for personal page
     *
     * @param page - personal page
     * @return true if succeeded
     */
    public boolean turnAlertForPersonalPageOn(PersonalInfo page) {
        if (!personalPagesFollowed.containsKey(page)) {//this fan doesn't follow this page
            return false;
        }
        personalPagesFollowed.replace(page, true);
        return true;
    }

    /**
     * UC 3.4 - submit a complaint form
     *
     * @param complaintForm - complaint form filled by fan in GUI
     */
    public void submitComplaintForm(ComplaintForm complaintForm) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        complaintForm.setFanSubmitingForm(this);
        system.addComplaint(complaintForm);
    }

    /**
     * UC - 3.5 - view search history
     *
     * @return list of search history
     */
    public LinkedList<String> viewSearchHistory() {
        return searchHistory;
    }

    /**
     * UC - 3.6 - view personal details
     *
     * @return - array list holding pair of details type and value which will be displayed in future GUI
     */
    public ArrayList<Pair<String, String>> viewPersonalDetails() {
        ArrayList<Pair<String, String>> personalDetails = new ArrayList<>();
        personalDetails.add(new Pair<>("Name", this.getName()));
        personalDetails.add(new Pair<>("Password", this.getPassword()));
        // MAYBE personalDetails.add(new Pair<>("ID",Integer.toString(this.getId())));
        return personalDetails;
    }

    /**
     * UC 3.6 - edit personal details, this func for password
     *
     * @param newPassword - new password
     * @return - true if password was changed
     */
    public boolean changePassword(String newPassword) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        return system.changeUserPassword(this, newPassword);
    }

    public boolean changeUserName(String newUserName) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        return system.changeUserName(this, newUserName);
    }

    /**
     * UC - 2.4 - also needs to be applied for Fan
     * view details about coach/player/team ( personal Page) or season/league etc.
     *
     * @param object - object user wish to view
     * @return - true if view is able
     */
    public boolean view(Object object) {
        if (object instanceof PersonalInfo) {
            ((PersonalInfo) object).viewPersonalPage();
            return true;
        } else if (object instanceof Season) {
            //todo - add view func for season
            return true;
        } else if (object instanceof Leaugue) {
            //todo - add view func for league
            return true;
        } else if (object instanceof Game) {
            //todo - MAYBE add view func for game
            return true;
        } else {
            return false;
        }
    }

    /**
     * search UC - 2.5 - also needs to be applied for Fan
     *
     * @param str      - query
     * @param searcher - searching by abstract searcher , searcher type will be defined in GUI
     * @return - Hashset returned by searcher
     */
    public HashSet<Object> search(String str, Searcher searcher) {

        searcher.search(str);
        searchHistory.add(str);
        return searcher.getAnswer();
    }


    /**
     * UC 11
     *
     * @param game
     * @param team
     * @return
     */
    public double useRecommandationSystem(Game game, Team team) {
        return recommendationSystem.recommendTeamOddsOfWinning(game, team);
    }
    /*getSet*/

    public HashMap<PersonalInfo, Boolean> getPersonalPagesFollowed() {
        return personalPagesFollowed;
    }

    public void setPersonalPagesFollowed(HashMap<PersonalInfo, Boolean> personalPagesFollowed) {
        this.personalPagesFollowed = personalPagesFollowed;
    }


    @Override
    public String toString() {
        return "Fan{" +
                ", searchHistory=" + searchHistory.toString() +
                '}';
    }
}
