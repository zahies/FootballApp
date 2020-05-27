//package Domain.Users;
//
//import Domain.FootballManagmentSystem;
//import Domain.SeasonManagment.*;
//import FootballExceptions.*;
//import javafx.util.Pair;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.LinkedList;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class CommissionerTest {
//
//
//    private static Object LinkedList;
//    String name;
//    int id;
//    Commissioner commissioner;
//    Leaugue leaugue;
//    Leaugue leaugue1;
//    Leaugue leaugue2;
//    Leaugue leaugue3;
//    Leaugue leaugue4;
//    Season season;
//    Referee referee;
//    TeamOwner teamOwner1;
//    TeamOwner teamOwner2;
//    Team teamAway;
//    int id1;
//    int id2;
//    String name1;
//    String name2;
//    String realName1;
//    String realName2;
//    String teamName1;
//    String teamName2;
//
//    @Before
//    public void init(){
//        name = "Tzah";
//        id = 333;
//        commissioner = new Commissioner(name, id, "222", "Tzahi");
//        leaugue = new Leaugue();
//        leaugue1 = new Leaugue();
//        leaugue2 = new Leaugue();
//        leaugue3 = new Leaugue();
//        leaugue4 = new Leaugue();
//        season = new Season(2020);
//        referee = new Referee("Jhon", "Snow",111, "Stark", RefereeType.Secondary);
//        id1 = 444;
//        id2 = 555;
//        name1 = "Jamie";
//        realName1 = "Lanister";
//        teamName1= "The Lanisters";
//        teamOwner1 = new TeamOwner(name1, realName1, 789, "kingsLanding", id1);
//        teamAway = new Team(teamName1, teamOwner1);
//    }
//
//
//
//    @Test (expected = LeagueIDAlreadyExist.class)
//    public void defineLeague() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
//        commissioner.defineLeague(123);
//        commissioner.defineLeague(123);
//    }
//
//    @Test (expected = SeasonYearAlreadyExist.class)
//    public void addSeasonToLeague() throws LeagueIDAlreadyExist, IDWasNotEnterdException, SeasonYearAlreadyExist {
//        commissioner.defineLeague(333);
//        commissioner.addSeasonToLeague(1995, leaugue3);
//        commissioner.addSeasonToLeague(1995, leaugue3);
//    }
//
//
//    @Test
//    public void addSeasonToLeague1() throws LeagueIDAlreadyExist, IDWasNotEnterdException, SeasonYearAlreadyExist {
//        commissioner.addSeasonToLeague(1995, leaugue);
//        Season ans = leaugue.getSeasonByYear(1995);
//        boolean flag = false;
//        if (1995 == (ans.getYear())){
//            flag=true;
//        }
//        assertTrue(flag);
//    }
//
//
//    /*
//    // FIXME: 2020-04-24
//    @Test
//    public void defineReferee() throws UnknownHostException, RefereeEmailWasNotEntered {
//        referee.setEmail("gmail.com");
//        commissioner.defineReferee(referee);
//        Referee ans = FootballManagmentSystem.getInstance().getAllRefs().get(0);
//        boolean flag = false;
//        if (referee.equals(ans)){
//            flag=true;
//        }
//        assertTrue(flag);
//    }
//
//
//
//    // FIXME: 2020-04-24
//    @Test
//    public void defineReferee1() throws UnknownHostException, RefereeEmailWasNotEntered {
//        commissioner.defineReferee(referee);
//        commissioner.defineReferee(referee.getName());
//    }
//
//    @Test (expected = LeagueNotFoundException.class)
//    public void addRefereeToSeason() throws LeagueNotFoundException {
//        commissioner.addRefereeToSeason(leaugue.getID(), 1995, referee);
//    }
//    */
//
//    @Test
//    public void addRefereeToSeason1() throws LeagueNotFoundException, LeagueIDAlreadyExist, IDWasNotEnterdException, SeasonYearAlreadyExist {
//        leaugue.setId(5);
//        leaugue.setLeagueIntoSystem();
//        commissioner.addSeasonToLeague(1995,leaugue);
//        FootballManagmentSystem.getInstance().addReferee(referee);
//        commissioner.addRefereeToSeason(leaugue.getID(), 1995, referee);
//        int ans = FootballManagmentSystem.getInstance().getAllRefs().size();
//        assertEquals(10,ans);
//    }
//
//
//    @Test
//    public void setNewScorePolicy() throws LeagueIDAlreadyExist, IDWasNotEnterdException, SeasonYearAlreadyExist, LeagueNotFoundException {
//        IScorePolicy scorePolicy = new DefaultIScorePolicy();
//        leaugue1.setId(6);
//        leaugue1.setLeagueIntoSystem();
//        commissioner.addSeasonToLeague(1995, leaugue1);
//        commissioner.setNewScorePolicy(leaugue1.getID(), 1995, scorePolicy);
//        Season ans = leaugue1.getSeasonByYear(1995);
//        IScorePolicy result = ans.getScorePolicy();
//        boolean flag = false;
//        if (scorePolicy.equals(result)) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }
//
//
//
//    @Test
//    public void setNewPlaceTeamsPolicy() throws LeagueIDAlreadyExist, IDWasNotEnterdException, SeasonYearAlreadyExist, LeagueNotFoundException {
//        IPlaceTeamsPolicy placeTeamsPolicy = new DefaultTeamsPolicy();
//        leaugue4.setId(444);
//        leaugue4.setLeagueIntoSystem();
//        commissioner.addSeasonToLeague(1995, leaugue4);
//        commissioner.setNewPlaceTeamsPolicy(leaugue4.getID(), 1995, placeTeamsPolicy);
//        Season ans = leaugue4.getSeasonByYear(1995);
//        IPlaceTeamsPolicy result = ans.getPlaceTeamsPolicy();
//        boolean flag = false;
//        if (placeTeamsPolicy.equals(result)) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }
//
//
//
//    @Test
//    public void defineBudgetControl() {
//        ICommissionerRule iCommissionerRule = new DefaultCommissionerRule();
//        FootballManagmentSystem.getInstance().addTeam(teamAway);
//        commissioner.defineBudgetControl(iCommissionerRule);
//        ICommissionerRule result = teamAway.getControlBudget().getICommissionerRule();
//        boolean flag = false;
//        if (iCommissionerRule.equals(result)) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }
//
//
//
//    @Test
//    public void addToFinanceAssociationActivity() {
//        commissioner.addToFinanceAssociationActivity("Food", 1000);
//        commissioner.addToFinanceAssociationActivity("Water", 100);
//        commissioner.addToFinanceAssociationActivity("Shoes", 10030);
//        LinkedList<Pair<String,Integer>> result = commissioner.financeAssociationActivity;
//        boolean flag = false;
//        if (3 == (result.size())) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }
//
//
//    @Test
//    public void delFromFinanceAssociationActivity() throws FinanceAssActivityNotFound {
//        Pair<String,Integer> pair = new Pair<>("Food", 1000);
//        commissioner.addToFinanceAssociationActivity("Food", 1000);
//        commissioner.addToFinanceAssociationActivity("Water", 100);
//        commissioner.addToFinanceAssociationActivity("Shoes", 10030);
//        commissioner.delFromFinanceAssociationActivity(pair);
//        LinkedList<Pair<String,Integer>> result = commissioner.financeAssociationActivity;
//        boolean flag = false;
//        if (2 == (result.size())) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }
//
//
//    @Test (expected = FinanceAssActivityNotFound.class)
//    public void delFromFinanceAssociationActivity1() throws FinanceAssActivityNotFound {
//        Pair<String,Integer> pair = new Pair<>("Food", 1000);
//        commissioner.addToFinanceAssociationActivity("Food", 1000);
//        commissioner.addToFinanceAssociationActivity("Water", 100);
//        commissioner.addToFinanceAssociationActivity("Shoes", 10030);
//        commissioner.delFromFinanceAssociationActivity(new Pair<>("Food", 20));
//        LinkedList<Pair<String,Integer>> result = commissioner.financeAssociationActivity;
//        boolean flag = false;
//        if (2 == (result.size())) {
//            flag = true;
//        }
//        assertTrue(flag);
//    }
//
//
//    /*
//    @Test(expected = NotEnoughTeamsInLeague.class)
//    public void runPlacingAlgo() throws NotEnoughTeamsInLeague {
//        commissioner.runPlacingAlgo(leaugue.getID(),1995);
//    }
//    */
//
//}