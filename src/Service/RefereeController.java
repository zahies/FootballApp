package Service;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Events.AGameEvent;
import Domain.SeasonManagment.Game;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import FootballExceptions.*;

import java.sql.SQLException;

public class RefereeController extends MemberController {


    /**
     * 10.1
     */
    public void changeName(Referee referee, String name) {
        try {
            referee.changeName(name);
        } catch (UserInformationException ue) {
            System.out.println(ue.getMessage());
        }
    }

    /**
     * 10.1
     */
    public void changeTraining(Referee referee, RefereeType type) {
        try {
            referee.changeTraining(type);
        } catch (UserInformationException ue) {
            System.out.println(ue.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        }

    }


    /**
     * 10.2
     */
    public void watchGame(Referee referee, Game game) {
        try {
            referee.watchGame(game);
        } catch (RefereeNotPlacedException re) {
            System.out.println(re.getMessage());
        }

    }

    /**
     * 10.3
     */
    public void addEventToGame(Referee referee, String eventType, double minute, Game game, Player playerWhoCommit) throws PersonalPageYetToBeCreatedException {
        try {
            referee.addEventToGame(eventType, minute, game, playerWhoCommit);
        } catch (EventNotMatchedException ee) {
            System.out.println(ee.getMessage());
        }

    }


    /**
     * 10.4
     */
    public void editEventsAfterGame(Referee referee, Game game, AGameEvent oldEvent, AGameEvent newEvent) {
        try {
            referee.editEventsAfterGame(game, oldEvent, newEvent);
        } catch (NoPermissionException ne) {
            System.out.println(ne.getMessage());
        }

    }


    /**
     * 10.4
     */
    public void addReportForGame(Referee referee, Game game) {
        try {
            referee.addReportForGame(game);
        } catch (NoPermissionException ne) {
            System.out.println(ne.getMessage());
        }
    }

}
