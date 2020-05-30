package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.CoachesDAL;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.*;
import Domain.Users.*;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.*;

public class CommissionerControllerTest {

    CommissionerController commissionerController;
    String name;
    int id;
    Commissioner commissioner;
    Leaugue leaugue;
    Leaugue leaugue2;
    Leaugue leaugue3;
    Season season;
    Referee referee;
    TeamOwner teamOwner1;
    Team teamAway;
    UUID id1;
    UUID id2;
    String name1;
    String realName1;
    String teamName1;



    @Before
    public void init() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        name = "Tzah";
        id = 3333;
        commissioner = new Commissioner(name, id, "222", "Tzahi");
        leaugue = new Leaugue();
        leaugue.setId(111);
        leaugue2 = new Leaugue();
        leaugue2.setId(222);
        leaugue2.setLeagueIntoSystem();
        leaugue3 = new Leaugue();
        leaugue3.setId(333);
        // leaugue3.setLeagueIntoSystem();
        season = new Season(2020);
        referee = new Referee("Jhon", "Snow", 111, "Stark", RefereeType.Secondary);
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        name1 = "Jamie";
        realName1 = "Lanister";
        teamName1= "The Lanisters";
        teamOwner1 = new TeamOwner(name1, realName1, 789, "kingsLanding", id1);
        teamAway = new Team(teamName1, teamOwner1);
        commissionerController = new CommissionerController();

    }




    @Test
    public void setNewScorePolicy() throws UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, NoConnectionException {
        Boolean flag = commissionerController.setNewScorePolicy(name, leaugue.getID(), 2020,  1, 2, 3);
        assertTrue(flag);
    }

    @Test
    public void setNewPlaceTeamsPolicy() throws UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, NoConnectionException {
        Boolean flag = commissionerController.setNewPlaceTeamsPolicy(name, leaugue.getID(), 2020, 3);
        assertTrue(flag);
    }


    @Test
    public void defineBudgetControl() throws UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, NoConnectionException {
        Boolean flag = commissionerController.defineBudgetControl(name, 90, "checkcheck");
        assertTrue(flag);
    }
}