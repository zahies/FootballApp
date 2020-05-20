package AcceptanceTests;

import Domain.SeasonManagment.*;
import Domain.Users.Commissioner;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import Domain.Users.TeamOwner;
import FootballExceptions.IDWasNotEnterdException;
import FootballExceptions.LeagueIDAlreadyExist;
import FootballExceptions.SeasonYearAlreadyExist;
import Service.CommissionerController;
import org.junit.Before;
import org.junit.Test;

public class CommissionerControllerTest {

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
    int id1;
    int id2;
    String name1;
    String realName1;
    String teamName1;
    CommissionerController commissionerController;


    @Before
    public void init() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        name = "Tzah";
        id = 333;
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
        id1 = 444;
        id2 = 555;
        name1 = "Jamie";
        realName1 = "Lanister";
        teamName1= "The Lanisters";
        teamOwner1 = new TeamOwner(name1, realName1, 789, "kingsLanding", id1);
        teamAway = new Team(teamName1, teamOwner1);
        commissionerController = new CommissionerController();
    }


    @Test
    public void defineLeaguePositive() {
        commissionerController.defineLeague(commissioner,111);
    }

    @Test
    public void defineLeagueNegative() {
        commissionerController.defineLeague(commissioner,111);
        commissionerController.defineLeague(commissioner,111);
    }






    @Test
    public void addSeasonToLeaguePositive() {
        commissionerController.addSeasonToLeague(commissioner,2020,leaugue);
    }

    @Test(expected = NumberFormatException.class)
    public void addSeasonToLeagueNegative() {
        commissionerController.addSeasonToLeague(commissioner,Integer.parseInt(null),leaugue);
    }






    @Test
    public void addRefereeToSeasonPositive() throws LeagueIDAlreadyExist, SeasonYearAlreadyExist {
        leaugue3.addSeasonToLeagueByYear(2007);
        commissionerController.addRefereeToSeason(commissioner,333,2007,referee);
    }

    @Test
    public void addRefereeToSeasonNegative() {
        commissionerController.addRefereeToSeason(commissioner,333,2007,referee);
    }






    @Test
    public void setNewScorePolicyPositive() {
        IScorePolicy scorePolicy = new DefaultIScorePolicy();
        commissionerController.setNewScorePolicy(commissioner,111,2020,scorePolicy);
    }

    @Test
    public void setNewScorePolicyNegative() {
        IScorePolicy scorePolicy = new DefaultIScorePolicy();
        commissionerController.setNewScorePolicy(commissioner,00001,2020,scorePolicy);
    }





    @Test
    public void setNewPlaceTeamsPolicyPositive() {
        IPlaceTeamsPolicy teamsPolicy = new DefaultTeamsPolicy();
        commissionerController.setNewPlaceTeamsPolicy(commissioner,111,2020,teamsPolicy);
    }

    @Test
    public void setNewPlaceTeamsPolicyNegative() {
        IPlaceTeamsPolicy teamsPolicy = new DefaultTeamsPolicy();
        commissionerController.setNewPlaceTeamsPolicy(commissioner,11,2020,teamsPolicy);
    }



}