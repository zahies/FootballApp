package Domain.Users;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.UsersDAL.RefereesDAL;
import Domain.Alerts.IAlert;
import Domain.Events.*;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Game;
import Domain.SystemLog;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.*;

public class Referee extends Member implements Observer {

    private String email;
    private RefereeType type;
    private List<Game> games;
    private FootballManagmentSystem system;

    @Override
    public void update(Observable o, Object arg) {
        handleAlert((IAlert) arg);
    }

    public Referee(String name, String password, String real_Name, Queue<IAlert> alertsList, boolean isActive, boolean alertViaMail, String mailAddress, RefereeType type, List<Game> games) {
        super(name, password, real_Name, alertsList, isActive, alertViaMail, mailAddress);
        this.type = type;
        this.games = games;
    }

    public Referee(String name, String realname, int id, String password, RefereeType type) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        super(name, id, password, realname);
        this.type = type;
        games = new ArrayList<>();
        system = FootballManagmentSystem.getInstance();
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
                system.addReferee(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }
        new RefereesDAL().insert(this);
    }

    /**
     * edit details UC 10.1
     */
    public void changeName(String newName) throws UserInformationException {
        system.delReferee(super.name);
        super.name = newName;
        system.addReferee(this);
    }

    /**
     * edit details UC 10.1
     */
    public void changeTraining(RefereeType newTraining) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        system.delReferee(super.name);
        type = newTraining;
        system.addReferee(this);
        new RefereesDAL().update(this);
    }


    /**
     * 10.2
     */
    public void watchGame(Game game) throws RefereeNotPlacedException {
        boolean found = false;
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i) == game) {
                found = true;
                System.out.println("You're watching the game : team : " + game.getHome().getName() + " vs team : " + game.getAway().getName());
            }
        }
        if (!found) {
            throw new RefereeNotPlacedException("You're not placed to this game ! ");
        }
    }


    //UC - 10.3
    public void addEventToGame(String eventType, double minute, Game game, Player playerWhoCommit) throws EventNotMatchedException, PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        AGameEvent event = stringToEvent(eventType, minute, playerWhoCommit);
        if (event instanceof Substitution) {
            game.addSubtitutionEventToEventLog(event);
        } else {
            game.addEventToEventLog(event);
        }
        SystemLog.getInstance().UpdateLog("new event: " + eventType + "was added by " + this.getName());
    }


    public AGameEvent stringToEvent(String eventType, double minute, Player player) throws EventNotMatchedException {
        switch (eventType) {
            case "foul":
                return new Foul(minute, player);
            case "goal":
                return new Goal(minute, player);
            case "injury":
                return new Injury(minute, player);
            case "offside":
                return new OffSide(minute, player);
            case "red card":
                return new RedCard(minute, player);
            case "yellow card":
                return new YellowCard(minute, player);
            case "substitution":
                return new Substitution(minute);
            default:
                throw new EventNotMatchedException("there is not event like " + eventType);

        }


    }


    /**
     * 10.4 edit events 5 hours after the game
     */
    public void editEventsAfterGame(Game game, AGameEvent oldEvent, AGameEvent newEvent) throws NoPermissionException {
        Date gameDate = game.getDateGame();
        Calendar cal = Calendar.getInstance();
        cal.setTime(gameDate);
        cal.add(Calendar.HOUR_OF_DAY, 6); // adds 6 hours
        cal.add(Calendar.MINUTE, 30); // adds 30 min
        long time = new Date(System.currentTimeMillis()).getTime();
        time = cal.getTimeInMillis() - time;
        if (time < 0 || this.type != RefereeType.Main) {
            throw new NoPermissionException(" Have no permission to edit :(");
        } else {
            game.event_logger.events.remove(oldEvent);
            game.event_logger.events.add(newEvent);
            SystemLog.getInstance().UpdateLog("event edited: " + newEvent.getClass() + "was edited by " + this.getName());
        }


    }


    /**
     * 10.4 add report for game
     */
    public void addReportForGame(Game game) throws NoPermissionException {
        game.getHome().addToGamesHistory(game.getAway(), game);
        game.getAway().addToGamesHistory(game.getHome(), game);
        Date gameDate = game.getDateGame();
        Calendar cal = Calendar.getInstance();
        cal.setTime(gameDate);
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds 6 hours
        cal.add(Calendar.MINUTE, 30); // adds 30 min
        long time = new Date(System.currentTimeMillis()).getTime();
        String report = "";
        if (time < cal.getTimeInMillis() && this.type == RefereeType.Main) {
            report += "Report of Game Dated : " + gameDate.toString() + "\n";
            report += "Home Team: " + game.getHome().getId() + "\n";
            report += "Away Team: " + game.getAway().getId() + "\n";
            report += "Main Referee: " + game.getMainReferee().getName() + "\n";
            report += "Secondary Referee: " + game.getSeconderyReferee().getName() + "\n";
            report += "Events: " + "\n";
            for (int i = 0; i < game.event_logger.events.size(); i++) {
                report += "event Type: " + game.event_logger.events.get(i).toString() + ", minute of the event: " + game.event_logger.events.get(i).getGameMinute() + "\n";
            }
            report += "END OF REPORT";
            SystemLog log = SystemLog.getInstance();
            log.UpdateLog(report);
            log.UpdateLog("report to a game was added by " + this.getName());
        } else {
            throw new NoPermissionException("Can't write report ! because the time has passed or referee is not MAIN one.");
        }
    }

    public void addToGameList(Game game) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        games.add(game);
        new RefereesDAL().update(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RefereeType getType() {
        return type;
    }

    public void setTraining(RefereeType training) {
        this.type = training;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


    @Override
    public String toString() {
        return "Referee{" +
                "email='" + email + '\'' +
                ", type=" + type.toString() +
//                ", games=" + games +
//                ", system=" + system +
                '}';
    }
}
