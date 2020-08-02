package Domain.Users;

import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.IAlert;
import Domain.Events.*;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RefereeTest {
    private Referee refereeTest;
    private Referee refereeTest2;
    private Referee refereeTest3;
    private RefereeType refereeType;
    private RefereeType refereeType2;
    private Game game;
    private Player player;
    private Team teamHome;
    private Team teamOut;
    private TeamOwner ownerHome;
    private TeamOwner ownerOut;
    private Date date1;
    private IAlert alert;
    private TeamManager teamManager;
    private TeamManager teamManager2;
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();


    @Before
    public void setUp() throws InactiveTeamException, UnauthorizedTeamManagerException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException {
        refereeType = RefereeType.Main;
        refereeType2 = RefereeType.Secondary;
        refereeTest = new Referee("Yossi43","Yossi",1234,"0101",refereeType);
        refereeTest2 = new Referee("Paul33","Paul",1235,"0102",refereeType2);
        refereeTest3 = new Referee("Paul32","Pauli",12435,"0552",RefereeType.Main);
        ownerHome = new TeamOwner("yakir","yaki",35,"3535");
        ownerOut = new TeamOwner("yam","yami",32,"3536");
        teamHome = new Team("Tel Aviv",ownerHome);
        teamOut = new Team("Haifa",ownerOut);
        teamManager = new TeamManager("zachi","zachi",24,"234",3,teamHome,ownerHome);
        //teamManager2 = new TeamManager("zach","zach",22,"235",2,teamOut,ownerOut);
        //teamOut.addNewTeamManger(ownerOut,teamManager2,3);
        //teamHome.createPersonalPage(teamManager);
        //teamOut.createPersonalPage(teamManager2);
        date1 =  new Date("31/03/2020");
        game = new Game(teamOut,teamHome, date1 ,refereeTest,refereeTest2,null);
        player = new Player("Noam","noam",2424,"@35",6,null,null);
        alert = new ChangedGameAlert(date1,game);
    }

    @Test
    public void update() {
        refereeTest.update(game,alert);
        assertTrue(refereeTest.getAlertsList().contains(alert));
    }

    //    @Test
//    //// FIXME: 22/04/2020  after they will do the mail
//    public void update2() {
//        refereeTest.setEmail("noaweiss6@gmail.com");
//        refereeTest.setAlertViaMail(true);
//        refereeTest.update(game,alert);
//        assertTrue(refereeTest.getAlertsList().contains(alert));
//    }
    @Test
    public void update3() {
        refereeTest.setActive(true);
        refereeTest.update(game,alert);
    }

    @Test
    public void changeName() throws UserInformationException {
        refereeTest.changeName("Odelia");
        assertEquals("Odelia",refereeTest.getName());
    }

    @Test
    public void changeTraining() throws UserInformationException {
        Referee trainingTest= new Referee("moti76","a vsdv",2355,"325",RefereeType.Main);
        trainingTest.changeTraining(RefereeType.Secondary);
        assertEquals(RefereeType.Secondary,trainingTest.getType());
    }

    @Test
    public void watchGame() throws RefereeNotPlacedException {
        refereeTest.watchGame(game);
    }
    @Test(expected = RefereeNotPlacedException.class )
    public void watchGame2() throws RefereeNotPlacedException {
        refereeTest3.watchGame(game);
    }

    @Test(expected = PersonalPageYetToBeCreatedException.class)

    public void addEventToGame() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("foul",90.0,game,player);
        Foul foul = new Foul(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)foul.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)

    public void addEventToGame2() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("goal",90.0,game,player);
        Goal goal = new Goal(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)goal.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)

    public void addEventToGame3() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("offside",90.0,game,player);
        OffSide offside = new OffSide(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)offside.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)

    public void addEventToGame4() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("red card",90.0,game,player);
        RedCard red_card = new RedCard(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)red_card.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void addEventToGame5() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("red card",90.0,game,player);
        RedCard red_card = new RedCard(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)red_card.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void addEventToGame6() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("yellow card",90.0,game,player);
        YellowCard yel_card = new YellowCard(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)yel_card.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = EventNotMatchedException.class)
    public void addEventToGame7() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("something",90.0,game,player);
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void addEventToGame8() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("injury",90.0,game,player);
        Injury injury = new Injury(90,player);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)injury.getGameMinute(),(int)a.getGameMinute());
    }
    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void addEventToGame9() throws EventNotMatchedException, PersonalPageYetToBeCreatedException {
        refereeTest.addEventToGame("substitution",90.0,game,player);
        Substitution substitution = new Substitution(90);
        IEvent a = game.getEvent_logger().getEvents().get(0);
        assertEquals((int)substitution.getGameMinute(),(int)a.getGameMinute());
    }

    @Test
    public void stringToEvent() throws EventNotMatchedException {
        IEvent a= refereeTest.stringToEvent("foul",90,player);
        Foul foul = new Foul(90,null);
        assertEquals((int)foul.getGameMinute(),(int)a.getGameMinute());

    }

    @Test(expected = NoPermissionException.class)
    public void editEventsAfterGame() throws NoPermissionException {
        Foul foul = new Foul(90,player);
        Foul foul2 = new Foul(86,null);
        refereeTest2.editEventsAfterGame(game,foul,foul2);
    }

    @Test
    public void editEventsAfterGame2() throws NoPermissionException {
        Foul foul = new Foul(90,player);
        Foul foul2 = new Foul(86,null);
        refereeTest.editEventsAfterGame(game,foul,foul2);
        assertEquals(foul2.getGameMinute(),game.getEvent_logger().getEvents().get(0).getGameMinute(),0);
    }

    @Test
    public void addReportForGame() throws NoPermissionException {
        refereeTest.addReportForGame(game);
    }

    @Test(expected = NoPermissionException.class)
    public void addReportForGame2() throws NoPermissionException {
        refereeTest2.addReportForGame(game);
    }

    @Test
    public void setTraining() {
        RefereeType old = refereeTest3.getType();
        refereeTest3.setTraining(RefereeType.Secondary);
        assertNotEquals(old,refereeTest3.getType());
    }

    @Test
    //+getGames
    public void setGames() {
        List<Game> games = new LinkedList<>();
        games.add(game);
        refereeTest3.setGames(games);
        assertEquals(1,refereeTest3.getGames().size());
    }
    @Test
    //+getEmail
    public void setEmail(){
        refereeTest.setEmail("noaweiss6@gmail.com");
        assertEquals("noaweiss6@gmail.com",refereeTest.getEmail());

    }
}