package Domain.Users;

import Domain.FootballManagmentSystem;
import Domain.Searcher.Searcher;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Season;
import FootballExceptions.UserInformationException;

import java.util.HashSet;
import java.util.LinkedList;

public class Guest extends GeneralUser {

    FootballManagmentSystem system;

    public Guest() {
        system = FootballManagmentSystem.getInstance();
    }

    /**
     * register - UC - 2.2
     *
     * @param userName
     * @param pass
     * @param id
     * @param type
     * @return true if register succeeded
     */
    public boolean register(String userName, String realnamr, String pass, int id, String type) throws UserInformationException {
        return system.register(userName, realnamr, pass, id);
    }

    /**
     * login to system - UC - 2.3
     *
     * @param username
     * @param password
     * @return true if login succeeded
     */
    public LinkedList<Member> login(String username, String password) throws UserInformationException {
        return system.login(username, password);
    }

    /**
     * UC - 2.4
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
        } else if (object instanceof Leaugue) {
            //todo - add view func for league
        } else if (object instanceof Game) {
            //todo - MAYBE add view func for game
        }
        return true;
    }

    /**
     * search - UC - 2.5
     *
     * @param str      - query
     * @param searcher - searching by abstract searcher , searcher type will be defined in GUI
     * @return - Hashset returned by searcher
     */
    public HashSet<Object> search(String str, Searcher searcher) {
        searcher.search(str);
        return searcher.getAnswer();
    }

}
