package SpringControllers;

import DataAccess.UsersDAL.MembersDAL;
import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.GameEventAlert;
import Domain.Alerts.IAlert;
import Domain.Events.AGameEvent;
import Domain.Events.Foul;
import Domain.Events.IEvent;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.AlreadyFollowThisPageException;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import org.json.JSONObject;

import java.util.*;

public class AlertsController {


    Referee ref = new Referee("John", "Snow", 555, "Halisi", RefereeType.Secondary);
    Player player = new Player("Jamie", "Lanister", 666, "Sarsei", 222, "bla", new Date());
    Player player2 = new Player("Jamie", "Lanister", 666, "Sarsei", 222, "bla", new Date());
    RefereeType refereeType = RefereeType.Main;
    RefereeType refereeType2 = RefereeType.Secondary;
    Referee refereeTest = new Referee("Yossi43", "Yossi", 1234, "0101", refereeType);
    Referee refereeTest2 = new Referee("Paul33", "Paul", 1235, "0102", refereeType2);
    TeamOwner ownerHome = new TeamOwner("yakir", "yaki", 35, "3535");
    TeamOwner ownerOut = new TeamOwner("yam", "yami", 32, "3536");
    Team teamHome = new Team("Tel Aviv", ownerHome);
    Team teamOut = new Team("Haifa", ownerOut);
    Date date1 = new Date("31/03/2020");
    Game game = new Game(teamOut, teamHome, date1, ref, refereeTest2, null);
    List<Game> games = new LinkedList<>();
    Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);






    /** alert for online user */
    public String showAlerts(String username) {




        /**test all kinds of alerts:*/
        //1. ChanfgedGameAlert
        IAlert changeGameAlert = new ChangedGameAlert(new Date(), game);




        IEvent foul = new Foul(32);
        GameEventAlert al = new GameEventAlert(2,foul);

        Queue<IAlert> alerts = member.getAlertsList();

        String message = "";

        /** via MAIL */
        if (member.isAlertViaMail()){
            FootballManagmentSystem system = FootballManagmentSystem.getInstance();
            for (IAlert alert:alerts) {
                system.sendInvitationByMail(member.getMailAddress(),alert.getClass().getSimpleName(),alert.toString());
                member.deleteSpecificAlert(alert);
            }

            /** via APP */
        }else{
            alerts.add(changeGameAlert); //fixme
            if (alerts.size() == 0){
                message = " Have no messages.";
            }else{
                if (alerts.size() > 1){
                    message = "[";
                }
                for (IAlert alert:alerts) {
                    int i = 0;
                    JSONObject json = new JSONObject();
                    json.put(alert.getClass().getSimpleName(),alert.toString());
                    message += json.toString(2);
                    i++;
                    if (i<alerts.size()){
                        message+=",";
                    }
                    member.deleteSpecificAlert(alert);
                }
                if (alerts.size() > 1){
                    message += "]";
                }
            }
        }
        return message;
    }



/*
    public List<IAlert> showAlerts(String username) {
//        Member member = new MembersDAL().select(username);
//        Player player = new Player("Jamie", "Lanister", 666 , "Sarsei", 222, "bla", new Date());
////        RefereeType refereeType = RefereeType.Main;
////        Referee refereeTest = new Referee("Yossi43", "Yossi", 1234, "0101", refereeType);
////        Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);
//        Referee ref = new Referee("John", "Snow", 555, "Halisi", RefereeType.Secondary);
//        RefereeType refereeType2 = RefereeType.Secondary;
//        Referee refereeTest2 = new Referee("Paul33","Paul",1235,"0102",refereeType2);
//        TeamOwner ownerHome = new TeamOwner("yakir", "yaki", 35, "3535");
//        TeamOwner ownerOut = new TeamOwner("yam", "yami", 32, "3536");
//        Team teamHome = new Team("Tel Aviv", ownerHome);
//        Team teamOut = new Team("Haifa", ownerOut);
//        Date  date1 =  new Date("31/03/2020");
//
//        teamHome.setInfo(new PersonalInfo(94));
//        teamHome.setInfo(new PersonalInfo(95));
//
//
//        Fan fan = new Fan("great","Noa",4325,"1234");
//        List<PersonalInfo> pageToFollowTest = new ArrayList<>();
//        pageToFollowTest.add(teamHome.getInfo());
//        try {
//            fan.addPersonalPagesToFollow(pageToFollowTest);
//        } catch (AlreadyFollowThisPageException e) {
//            e.printStackTrace();
//        }

//        Game game = new Game(teamOut, teamHome, date1, ref, refereeTest2, null);
//        try {
//            game.addEventToEventLog(foul);
//        } catch (PersonalPageYetToBeCreatedException e) {
//            e.printStackTrace();
//        }

        Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);
        Player player = new Player("Jamie", "Lanister", 666 , "Sarsei", 222, "bla", new Date());
        AGameEvent foul = new Foul(34, player);
        GameEventAlert al = new GameEventAlert(2,foul);

        Queue<IAlert> alerts = member.getAlertsList();
        alerts.add(al);
        String message = "";

        if (member.isAlertViaMail()){
            FootballManagmentSystem system = FootballManagmentSystem.getInstance();
            for (IAlert alert:alerts) {
                system.sendInvitationByMail(member.getMailAddress(),alert.getClass().getSimpleName(),alert.toString());
                member.deleteSpecificAlert(alert);
            }

        }else{
            return (List)alerts;
        }
        return null;
    }
*/









}
