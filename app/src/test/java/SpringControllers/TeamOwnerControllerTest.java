package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Season;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class TeamOwnerControllerTest {

    TeamOwnerController teamOwnerController;
    String name;
    int id;
    Commissioner commissioner;
    Leaugue leaugue;
    Leaugue leaugue2;
    Leaugue leaugue3;
    Season season;
    Referee refereeMain;
    Referee refereeSec;
    Referee referee;
    TeamOwner teamOwner1;
    Team teamAway;
    Team teamHome;
    UUID id1;
    UUID id2;
    String name1;
    String realName1;
    String teamName1;
    String name2;
    String realName2;
    String teamName2;
    Game game;
    Player player;


    @Before
    public void init() throws LeagueIDAlreadyExist, IDWasNotEnterdException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        name = "Tzah";
        id = 3333;
        //commissioner = new Commissioner(name, id, "222", "Tzahi");
        //leaugue = new Leaugue();
        //leaugue2 = new Leaugue();
        //leaugue2.setLeagueIntoSystem();
        //leaugue3 = new Leaugue();
        // leaugue3.setLeagueIntoSystem();
        //season = new Season(2020);
        //refereeMain = new Referee("Jhon", "Snow", 111, "Stark", RefereeType.Main);
        //refereeSec = new Referee("Hola", "Choco", 121, "Hi", RefereeType.Secondary);
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        name1 = "Jamie";
        realName1 = "Lanister";
        teamName1= "The Lanisters";
        name1 = "Aria";
        realName1 = "Stark";
        teamName1= "The Starks";
        //teamOwner1 = new TeamOwner(name1, realName1, 789, "kingsLanding", id1);
        //teamAway = new Team(teamName1, teamOwner1);
        //teamAway = new Team(teamName1, teamOwner1);
        teamOwnerController = new TeamOwnerController();
        //referee = new Referee("bla", "blabla", 333, "123", RefereeType.Secondary);
        //game = new Game(teamAway, teamHome, new Date(), refereeMain, refereeSec, season);
        //Player player = new Player("Romi", "mi", 5,"sss", 111, "back", new Date());
    }

    @Test
    public void sendRegisterRequestForNewTeam() {
        boolean flag = teamOwnerController.sendRegisterRequestForNewTeam(teamOwner1.getName(), teamAway.getName(), leaugue.getObjectID().toString(), 1999);
        assertTrue(flag);
    }


}