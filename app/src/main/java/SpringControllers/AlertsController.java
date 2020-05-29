package SpringControllers;

import DataAccess.UsersDAL.MembersDAL;
import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.GameEventAlert;
import Domain.Alerts.IAlert;
import Domain.Alerts.PersonalPageAlert;
import Domain.Events.AGameEvent;
import Domain.Events.Foul;
import Domain.Events.IEvent;
import Domain.FootballManagmentSystem;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;
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
    TeamOwner owner = new TeamOwner("asif","Asif",333,"234");
    Team teamHome = new Team("Tel Aviv", owner);
    Team teamOut = new Team("Haifa", ownerOut);
    Date date1 = new Date("31/03/2020");
    Game game = new Game(teamOut, teamHome, date1, ref, refereeTest2, null);
    List<Game> games = new LinkedList<>();
    Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);
    Fan fan = new Fan("Apolo", "wer", 333, "Romi");
    private List<PersonalInfo> infos = new ArrayList<>();
    Member futureManager = new Fan("manager","mani",4325,"1234");
    Member futureManager2 = new Fan("manager2","mani2",43252,"12342");
    HashMap<PersonalInfo,Boolean> pageToFollowTest = new HashMap<>();
    IAlert alert;
    ProfileContent profileContent = new ProfileContent();









    /** alert for online user */
    public String showAlerts(String username) {
//        player.createPersonalPage();
//        infos.add(player.getInfo());
//        try {
//            fan.addPersonalPagesToFollow(infos);
//        } catch (AlreadyFollowThisPageException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            ref.addEventToGame("bla", 22, game, player);
//        } catch (EventNotMatchedException e) {
//            e.printStackTrace();
//        } catch (PersonalPageYetToBeCreatedException e) {
//            e.printStackTrace();
//        }

        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        try {
            owner.setTeam(teamHome);
            owner.assignNewTeamManager(futureManager2,5);
        } catch (MemberIsAlreadyTeamOwnerException e) {
            e.printStackTrace();
        } catch (MemberIsAlreadyTeamManagerException e) {
            e.printStackTrace();
        } catch (TeamOwnerWithNoTeamException e) {
            e.printStackTrace();
        } catch (UnauthorizedTeamOwnerException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            e.printStackTrace();
        }
        try {
            futureManager2 = system.getMemberInstanceByKind(futureManager2.getName(),"Team Manager");
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        }
        try {
            owner.editManagerPermissions(futureManager2,"Create Personal Page",true);
        } catch (PersonalPageYetToBeCreatedException e) {
            e.printStackTrace();
        } catch (UnauthorizedPageOwnerException e) {
            e.printStackTrace();
        } catch (UnauthorizedTeamOwnerException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (TeamOwnerWithNoTeamException e) {
            e.printStackTrace();
        }
        try {
            ((TeamManager)futureManager2).createPersonalPageForTeam();
        } catch (UnauthorizedPageOwnerException e) {
            e.printStackTrace();
        } catch (UnauthorizedTeamManagerException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            e.printStackTrace();
        }
        infos.add(teamHome.getInfo());
        try {
            fan.addPersonalPagesToFollow(infos);
        } catch (AlreadyFollowThisPageException e) {
            e.printStackTrace();
        }
        pageToFollowTest = fan.getPersonalPagesFollowed();
        fan.turnAlertForPersonalPageOn(teamHome.getInfo());
        alert = new PersonalPageAlert(teamHome.getInfo(),profileContent);
        fan.update(game,alert);




        /**test all kinds of alerts:*/
        //1. ChanfgedGameAlert
        IAlert changeGameAlert = new ChangedGameAlert(new Date(), game);




        IEvent foul = new Foul(32);
        GameEventAlert al = new GameEventAlert(2,foul);

        Queue<IAlert> alerts = fan.getAlertsList();

        String message = "";

        /** via MAIL */
        if (member.isAlertViaMail()){
           // FootballManagmentSystem system = FootballManagmentSystem.getInstance();
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
