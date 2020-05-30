package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.CoachesDAL;
import Domain.Alerts.IAlert;
import Domain.Alerts.PersonalPageAlert;
import Domain.FootballManagmentSystem;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

public class AlertsControllerTest {



    Referee ref;
    Player player;
    Player player2;
    RefereeType refereeType;
    RefereeType refereeType2;
    Referee refereeTest;
    Referee refereeTest2;
    TeamOwner ownerHome;
    TeamOwner ownerOut;
    TeamOwner owner;
    Team teamHome;
    Team teamOut;
    Date date1;
    Game game;
    List<Game> games;
    Member member;
    Fan fan;
    private List<PersonalInfo> infos;
    Member futureManager;
    Member futureManager2;
    HashMap<PersonalInfo,Boolean> pageToFollowTest;
    IAlert alert;
    ProfileContent profileContent;
    FootballManagmentSystem system;
    AlertsController alertsController;



    @Before
    public void init() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {

        ref = new Referee("John", "Snow", 555, "Halisi", RefereeType.Secondary);
        player = new Player("Jamie", "Lanister", 666, "Sarsei", 222, "bla", new Date());
        player2 = new Player("Jamie", "Lanister", 666, "Sarsei", 222, "bla", new Date());
        refereeType = RefereeType.Main;
        refereeType2 = RefereeType.Secondary;
        refereeTest = new Referee("Yossi43", "Yossi", 1234, "0101", refereeType);
        refereeTest2 = new Referee("Paul33", "Paul", 1235, "0102", refereeType2);
        ownerHome = new TeamOwner("yakir", "yaki", 35, "3535");
        ownerOut = new TeamOwner("yam", "yami", 32, "3536");
        owner = new TeamOwner("asif","Asif",333,"234");
        teamHome = new Team("Tel Aviv", owner);
        teamOut = new Team("Haifa", ownerOut);
        date1 = new Date("31/03/2020");
        game = new Game(teamOut, teamHome, date1, ref, refereeTest2, null);
        games = new LinkedList<>();
        member = new Player("Ohana", "FSAF", 416, "123", 214, "GSDG", null);
        fan = new Fan("Apolo", "wer", 333, "Romi");
        infos = new ArrayList<>();
        futureManager = new Fan("manager","mani",4325,"1234");
        futureManager2 = new Fan("manager2","mani2",43252,"12342");
        pageToFollowTest = new HashMap<>();
        profileContent = new ProfileContent();
        system = FootballManagmentSystem.getInstance();
        alertsController = new AlertsController();



        //  cont = (APersonalPageContent)new PersonalPageContentDAL().select(contString);

    }




    @Test
    public void showAlerts() throws AlreadyFollowThisPageException, EventNotMatchedException, PersonalPageYetToBeCreatedException, UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UserIsNotThisKindOfMemberException, UnauthorizedPageOwnerException, UnauthorizedTeamManagerException, mightBeSQLInjectionException, SQLException, DuplicatedPrimaryKeyException, NoPermissionException, NoConnectionException, EmptyPersonalPageException {
        infos.add(player.getInfo());
        fan.addPersonalPagesToFollow(infos);
        ref.addEventToGame("bla", 22, game, player);
        owner.setTeam(teamHome);
        owner.assignNewTeamManager(futureManager2,5);
        futureManager2 = system.getMemberInstanceByKind(futureManager2.getName(),"Team Manager");
        owner.editManagerPermissions(futureManager2,"Create Personal Page",true);
        ((TeamManager)futureManager2).createPersonalPageForTeam();
        infos.add(teamHome.getInfo());
        fan.addPersonalPagesToFollow(infos);
        pageToFollowTest = fan.getPersonalPagesFollowed();
        fan.turnAlertForPersonalPageOn(teamHome.getInfo());
        alert = new PersonalPageAlert(teamHome.getInfo(), profileContent);
        fan.update(game,alert);
        Map<String, List<String>> msg = alertsController.showAlerts(fan.getName());
        assertNotNull(msg);
    }
}