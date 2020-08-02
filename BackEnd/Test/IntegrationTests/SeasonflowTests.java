package IntegrationTests;

import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.*;
import Domain.Users.*;
import FootballExceptions.*;
import Service.GuestController;
import Service.SystemManagerController;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SeasonflowTests {
    private static FootballManagmentSystem fms;
    private static Guest guest;
    private static Team team;
    private static Team team2;
    private static Team team3;
    private static Team team4;
    private static TeamOwner teamOwner;
    private static TeamOwner teamOwner2;
    private static TeamOwner teamOwner3;
    private static TeamOwner teamOwner4;
    private static GuestController guestController;
    private static Leaugue leaugue;
    private static Commissioner commissioner;

    private static SystemManager systemManager ;
    private static Referee ref2;
    private static Referee ref;
    private static Referee ref3;
    private static Referee ref4;
    SystemManagerController systemManagerC ;

    @Before
    public void init() throws UserInformationException {
       // season = new Season(2020);
        commissioner =  new Commissioner("AlonGeva", 123, "YO","avi nusbaum");
        leaugue = new Leaugue();
        fms = FootballManagmentSystem.getInstance();
        teamOwner = new TeamOwner("gabi1Queen20","avishai",2,"12345");
        teamOwner2 = new TeamOwner("gabiPrince120","avishai",21,"12345");
        teamOwner3 = new TeamOwner("gabiKing120","avishai",22,"12345");
        teamOwner4 = new TeamOwner("gabiKing0","avishai",25,"12345");
        team = new Team("Hapoel Haifa",teamOwner);
        team2 = new Team("Maccabi Haifa",teamOwner2);
        team3 = new Team("Hapoel Kosi-lam-lam",teamOwner3);
        team4 = new Team("Efrohei Tapash",teamOwner4);
        fms.addTeam(team);
        //fms.addMember(teamOwner);
        teamOwner.setTeam(team);
        guest = new Guest();
        ref = new Referee("ref1","theref",4,"3232",RefereeType.Main);
        ref2 = new Referee("ref2","theref2",5,"3232",RefereeType.Secondary);
        ref3 = new Referee("ref3","theref2",6,"3232",RefereeType.Main);
        ref4 = new Referee("ref4","theref2",57,"3232",RefereeType.Secondary);
        systemManager = (SystemManager) fms.getMemberByUserName("aviluzon").get(0);
        guestController = new GuestController();
        if(fms.getMemberByUserName("itamar12") == null)
            guestController.register(guest, "itamar12", "itamar", "1234", 1, null);
        systemManagerC = new SystemManagerController();

    }

    @Test
    public void seasonProgressTest()throws LeagueIDAlreadyExist, IDWasNotEnterdException, SeasonYearAlreadyExist, UserInformationException, LeagueNotFoundException, NotEnoughTeamsInLeague {
        //start
        leaugue.setId(32);
        leaugue.setLeagueIntoSystem();
        leaugue.addSeasonToLeagueByYear(2020);
        fms.addMember(commissioner);
        DefaultTeamsPolicy df = new DefaultTeamsPolicy();
        DefaultIScorePolicy ds = new DefaultIScorePolicy();
        commissioner.setNewPlaceTeamsPolicy(32,2020,df);
        commissioner.setNewScorePolicy(32,2020,ds);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team2);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team3);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team4);
        commissioner.addRefereeToSeason(32,2020,ref2);
        commissioner.addRefereeToSeason(32,2020,ref);
        commissioner.addRefereeToSeason(32,2020,ref3);
        commissioner.addRefereeToSeason(32,2020,ref4);
        commissioner.runPlacingAlgo(32,2020);
        Season season =leaugue.getSeasonByYear(2020);
        /// game scores
        Map<Integer,Team> table = new TreeMap<>();

        int i =0;
        for (Game game:leaugue.getSeasonByYear(2020).getGames()) {
            if(i==3) {
                game.setScoreAway(2);
                game.getAway().setScore(season.getScorePolicy().winVal()+ game.getAway().getScore());
                game.setScoreHome(1);
                game.getHome().setScore(season.getScorePolicy().looseVal()+ game.getHome().getScore());
            }
            else if (i==7){
                game.setScoreAway(0);
                game.getAway().setScore(season.getScorePolicy().looseVal()+ game.getAway().getScore());
                game.setScoreHome(2);
                game.getHome().setScore(season.getScorePolicy().winVal()+ game.getHome().getScore());
            }
            else if (i%2==0){
                game.setScoreAway(0);
                game.getAway().setScore(season.getScorePolicy().drowVal()+ game.getAway().getScore());
                game.setScoreHome(0);
                game.getHome().setScore(season.getScorePolicy().drowVal()+ game.getHome().getScore());
                System.out.println(game.getAway().getName() + " this is away team and she got "+season.getScorePolicy().drowVal()+" points"+game.getHome().getName() + " this is Home team and she got "+season.getScorePolicy().drowVal()+" points");

            }
            else {
                game.setScoreAway(4);
                game.getAway().setScore(season.getScorePolicy().winVal()+ game.getAway().getScore());
                game.setScoreHome(1);
                game.getHome().setScore(season.getScorePolicy().looseVal()+ game.getHome().getScore());
            }
            i++;
        }
        table.put(team.getScore(),team);
        table.put(team3.getScore(),team2);
        table.put(team2.getScore(),team3);
        table.put(team4.getScore(),team4);

    }
}
