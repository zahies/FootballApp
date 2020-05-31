package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.GameEventAlert;
import Domain.Alerts.IAlert;
import Domain.Alerts.IGameSubjective;
import Domain.Events.AGameEvent;
import Domain.Events.Event_Logger;
import Domain.FootballManagmentSystem;
import Domain.Users.Referee;
import FootballExceptions.NoPermissionException;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;
import java.util.*;

public class Game extends Observable {
    private UUID objectId;
    private Team away;
    private Team home;
    private Date dateGame;
    private Referee mainReferee;
    private Referee seconderyReferee;
    private int scoreHome;
    private int scoreAway;
    private Season season;
    public Event_Logger event_logger;
    private LinkedList<Observer> referees;
    private IAlert alert;

    /***FOR DB SELECT)*/
    public Game(UUID objectId, Team away, Team home, Date dateGame, Referee mainReferee, Referee seconderyReferee, int scoreHome, int scoreAway, Season season, LinkedList<Observer> referees, Event_Logger event_logger) {
        this.objectId = objectId;
        this.away = away;
        this.home = home;
        this.dateGame = dateGame;
        this.mainReferee = mainReferee;
        this.seconderyReferee = seconderyReferee;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.season = season;
        this.referees = referees;
        this.event_logger = event_logger;
    }

    public Game(Team away, Team home, Date dateGame, Referee mainReferee, Referee seconderyReferee, Season season) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        objectId = UUID.randomUUID();
        this.away = away;
        this.home = home;
        this.dateGame = dateGame;
        this.mainReferee = mainReferee;
        this.seconderyReferee = seconderyReferee;
        this.season = season;
        event_logger = new Event_Logger();
        referees = new LinkedList<>();
        referees.add(mainReferee);
        referees.add(seconderyReferee);
        new GamesDAL().insert(this);
        mainReferee.addToGameList(this);
        seconderyReferee.addToGameList(this);
    }

    //todo - add option to  notify ref when upcoming match date
    public void changeDate(Date newDate) throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        this.dateGame = newDate;
        IAlert newAlart = new ChangedGameAlert(new Date(), this);
        new GamesDAL().update(this);
        notifyReferees(newAlart);
    }

    public void notifyRefereesWithNewDate(Date newDate) {
        IAlert newAlart = new ChangedGameAlert(newDate, this);
        notifyReferees(newAlart);
    }


//    @Override
//    public String toString() {
//        String ans ="";
//
//    }


    @Override
    public String toString() {
        return "Game{" +
                "objectId=" + objectId.toString() +
                ", away=" + away +
                ", home=" + home +
                ", dateGame=" + dateGame +
                ", mainReferee=" + mainReferee +
                ", seconderyReferee=" + seconderyReferee +
                ", scoreHome=" + scoreHome +
                ", scoreAway=" + scoreAway +
                ", season=" + season +
                ", event_logger=" + event_logger +
                ", referees=" + referees +
                ", alert=" + alert +
                '}';
    }

    public void run() {

    }

    public void notifyReferees(IAlert newAlert) {
        for (Observer O : referees) {
            O.update(this, newAlert);
            newAlert.setNewID();
        }
    }

    public void notifyTeamfans(IAlert newAlert) throws PersonalPageYetToBeCreatedException {
        home.notifyTeam(newAlert, this);
        away.notifyTeam(newAlert, this);
    }


    //part of UC - 10.3 + alerting to followers
    public void addEventToEventLog(AGameEvent event) throws PersonalPageYetToBeCreatedException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        event.getPlayerWhocommit().changePlayerRate(event);
        event_logger.addEvent(event);
        IAlert alert = new GameEventAlert(event.getGameMinute(), event);
        notifyTeamfans(alert);
    }


    //part of UC - 10.3 + alerting to followers
    public void addSubtitutionEventToEventLog(AGameEvent event) throws PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        event_logger.addEvent(event);
        IAlert alert = new GameEventAlert(event.getGameMinute(), event);
        notifyTeamfans(alert);
    }

    public Team getAway() {
        return away;
    }

    public Team getHome() {
        return home;
    }

    public Date getDateGame() {
        return dateGame;
    }

    public Referee getMainReferee() {
        return mainReferee;
    }

    public Referee getSeconderyReferee() {
        return seconderyReferee;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    public Season getSeason() {
        return season;
    }

    public Event_Logger getEvent_logger() {
        return event_logger;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public void setDateGame(Date dateGame) throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        changeDate(dateGame);
    }

    public void setMainReferee(Referee mainReferee) {
        this.mainReferee = mainReferee;
    }

    public void setSeconderyReferee(Referee seconderyReferee) {
        this.seconderyReferee = seconderyReferee;
    }

    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }

    public void setScoreAway(int scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setEvent_logger(Event_Logger event_logger) {
        this.event_logger = event_logger;
    }

    public UUID getObjectId() {
        return objectId;
    }
}
