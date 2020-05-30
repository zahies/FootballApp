import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UsersDAL.MembersDAL;
import DataAccess.UsersDAL.PlayersDAL;
import DataAccess.UsersDAL.TeamManagerDAL;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;

import javax.mail.MessagingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {


    public static void main(String[] args) throws ParseException, UnknownHostException, UserInformationException, NotEnoughTeamsInLeague, EventNotMatchedException, PersonalPageYetToBeCreatedException, TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, UserIsNotThisKindOfMemberException, MessagingException, DuplicatedPrimaryKeyException, SQLException, mightBeSQLInjectionException, NoPermissionException, NoConnectionException {
//        MembersDAL membersDAL = new MembersDAL();
////        AssetsDAL assetsDAL = new AssetsDAL();
////        TeamOwnersDAL teamOwnersDAL = new TeamOwnersDAL();
////        TeamsDAL teamsDAL = new TeamsDAL();
////        PlayersDAL dal = new PlayersDAL();
//        TeamManagerDAL teamManagerDAL = new TeamManagerDAL();
////
////        Member member1 = new TeamOwner("Moshe", "Hogeg", 213412, "123456");
////        Team team = new Team("Maccabi333", ((TeamOwner) member1));
////        //boolean ans=((Player)member).createPersonalPage();
////        Member member = new TeamManager("Ohana", "Eli", 12341, "WQESD", 3123, team, ((TeamOwner) member1));
//
////        try {
////            ((TeamOwner)member1).assignNewTeamManager(FootballManagmentSystem.getInstance().getMemberInstanceByKind(((Player) member).getName(),"Player"),989874);
////
////        } catch (MemberIsAlreadyTeamOwnerException e) {
////            e.printStackTrace();
////        } catch (MemberIsAlreadyTeamManagerException e) {
////            e.printStackTrace();
////        }
//        try {
//
//
//            //dal.insert(member);
//            // teamOwnersDAL.insert(member1);
//            //teamsDAL.insert(team);
//            //teamManagerDAL.insert(FootballManagmentSystem.getInstance().getMemberInstanceByKind(member.getName(),"Team Manager"));
//            //dal.update(member,new Pair<>("RealName","Shlomo Bar Tov"));
//            //teamManagerDAL.update(member,new Pair<>("Permission",new Pair("Hire Coach",true)));
//            TeamManager teamManager = (TeamManager) teamManagerDAL.select("Ohana");
//            boolean ans = membersDAL.checkExist("Ohana", "members", "UserName");
//            System.out.println(teamManager.getPassword());
//            //teamOwnersDAL.update(member1,new Pair<>("UserName","Tzach"));
//            //membersDAL.update(member1,new Pair<>("RealName","Moshe Bar Simon Tov"));
//            //membersDAL.update(member1,new Pair<>("UserName","Moshe"));
//            // assetsDAL.update(member,new Pair<>("AssetVal",4654165));
//            System.out.println(ans);
//        } catch (SQLException | NoConnectionException e) {
//            e.printStackTrace();
//        } catch (mightBeSQLInjectionException e) {
//            e.printStackTrace();
//        }
        //((TeamOwner) member1).addAssetToTeam(member);


//        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
//        FootballManagmentSystem system1 = FootballManagmentSystem.getInstance();
//        System.out.println(system.getClass());
//        Referee referee = new Referee("zaza","zaza", 123,"12345678", RefereeType.Main);
//        Referee referee1 = new Referee("zaza","zaza", 123,"12345678", RefereeType.Main);
//        Season season = new Season(null,null,1900);
//        season.addRefereeToSeason(referee);
//        season.deleteRefereeFromSeasonByName("zaza");
//        TeamOwner owner = new TeamOwner("zazaza","zazaza",1231,"12345676");
//        Team team1 = new Team("haifa",owner);
//        Team team2 = new Team("haifa",owner);
//        season.addTeamToSeason(team1);
//        season.addTeamToSeason(team2);
//        season.addRefereeToSeason(referee);
//        season.addRefereeToSeason(referee1);
//        season.runPlacingTeamsAlgorithm();
//        Game game = new Game(team1,team2,new Date(),referee,referee,season);
//        //referee.addReportForGame(game);
//        system.addReferee(referee);
//        system.delReferee("zaza");
//        Player player1 = new Player("sasa","sasa",231,"1243432",432,"ss",new Date());
//        Player player2 = new Player("sasa","sasa",231,"1243432",432,"ss",new Date());
//
//        IEvent red = new RedCard(213,player1);
//        referee.addEventToGame("red card",34,game,player1);
//
//
//

//        ControlBudget controlBudget = new ControlBudget(23);
//        Team team = new Team("a",null, TeamStatus.Active,0,23,controlBudget);
//        system.addTeam(team);

//        try {
//            TeamOwner teamOwner = new TeamOwner("Moshe", "Moshe Hogeg", 203, "#123");
//            Team beitar = new Team("Beiter", teamOwner);
//            System.out.println("FSDFSD");
//            IAsset coach = new Coach("Roni", "roni levy", 266, "dsds", 5000, "fsdf", CoachRole.HeadCoach);
//            teamOwner.addAssetToTeam(coach);
//            IAsset player = new Player("CR7", "Ronaldo", 0, "sdasd", 1000, "Striker", null);
//            teamOwner.addAssetToTeam(player);
//            teamOwner.editAsset(player, 165165);
//            if (coach instanceof Coach) {
//                teamOwner.assignNewTeamOwner(system.getMemberInstanceByKind(((Coach) coach).getName(), "Coach"));
//            }
//            Member playerAsTeamManager=null;
//            if(player instanceof Player){
//                playerAsTeamManager = system.getMemberInstanceByKind(((Player) player).getName(),"Player");
//            }
//            teamOwner.assignNewTeamManager(playerAsTeamManager,45645);
//            playerAsTeamManager = system.getMemberInstanceByKind(playerAsTeamManager.getName(),"Team Manager");
//            teamOwner.editManagerPermissions(playerAsTeamManager,"Create Personal Page",true);
//            ((TeamManager)playerAsTeamManager).createPersonalPageForTeam();
//            teamOwner.editManagerPermissions(playerAsTeamManager,"Add Content To Personal Page",true);
//            ((TeamManager)playerAsTeamManager).addContentToTeamsPersonalPage(new ProfileContent());
//            ((TeamManager)playerAsTeamManager).addContentToTeamsPersonalPage(new NewsContent());
//            teamOwner.removeTeamManager(system.getMemberInstanceByKind(playerAsTeamManager.getName(),"Team Manager"));
//            teamOwner.changeTeamStatus(TeamStatus.Close);
//            teamOwner.changeTeamStatus(TeamStatus.Active);
//
//            teamOwner.addAssetToTeam(new Field());
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (InactiveTeamException e) {
//
//        } catch (MemberIsAlreadyTeamManagerException e) {
//            e.printStackTrace();
//        } catch (TeamOwnerWithNoTeamException e) {
//            e.printStackTrace();
//        } catch (UnauthorizedTeamOwnerException e) {
//            e.printStackTrace();
//        } catch (InvalidTeamAssetException e) {
//            e.printStackTrace();
//        } catch (MemberIsAlreadyTeamOwnerException e) {
//            e.printStackTrace();
//        } catch (UnauthorizedPageOwnerException e) {
//            e.printStackTrace();
//        } catch (PersonalPageYetToBeCreatedException e) {
//            e.printStackTrace();
//        } catch (UnauthorizedTeamManagerException e) {
//            e.printStackTrace();
//        }

//        system.sendInvitationByMail("kaprizahi@gmail.com","Hi","hiiiii");
//
//        /** constraint 7 - balanced budget  */
//        Date date1 = new Date("31/03/2020");
//        Date date2 = new Date("30/06/2020");
//        Date date3 = new Date("30/09/2020");
//        Date date4 = new Date("31/12/2020");
//
//        Timer timer = new Timer();
//        Timer timer1 = new Timer();
//        TimerTask task = FootballManagmentSystem.getInstance();
//        TimerTask task1 = FootballManagmentSystem.getInstance();
//        timer.schedule(task, date1);
//        timer1.schedule(task, date2);
//        timer.schedule(task, date3);
//        timer.schedule(task, date4);

        Commissioner commissioner = new Commissioner("Shira", 313, "222", "Tzahi");
        commissioner.runPlacingAlgo(333,222);
        FootballManagmentSystem footballManagmentSystem =  FootballManagmentSystem.getInstance();
      //  footballManagmentSystem.sendInvitationByMail("shira.wert@gmail.com", "bla", "bla");
        footballManagmentSystem.sendInvitationByMail("kaprizahi@gmail.com","hi there","nice");


    }
}
