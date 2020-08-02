package Domain.SeasonManagment;

import Domain.Events.AGameEvent;
import Domain.Events.Foul;
import Domain.FootballManagmentSystem;
import Domain.Users.*;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import java.util.Date;

import static Domain.SeasonManagment.BudgetActivity.GameIncome;
import static org.junit.Assert.*;

public class TeamTest {

    TeamOwner teamOwner1;
    TeamOwner teamOwner2;
    Team teamAway;
    Team teamHome;
    int assetID;
    Field field;
    int id1;
    int id2;
    String name1;
    String name2;
    String realName1;
    String realName2;
    String teamName1;
    String teamName2;
    int budget11;
    int budget12;
    int totalBudget1;
    HashMap <Integer, IAsset> teamPlayers;
    Player player1;
    Player player2 ;
    Coach coach;
    TeamManager teamManager;
    Date date;
    Referee refereeSec;
    Referee refereeMain;
    Season season;
    Season season1;
    Game game;
    Player player;
    AGameEvent foul;
    FootballManagmentSystem system;
    Member member;





    @Before
    public void init() throws InactiveTeamException, UnauthorizedTeamOwnerException {
        id1 = 444;
        id2 = 555;
        name1 = "Guy";
        name2 = "Amit";
        realName1 = "Alice";
        realName2 = "Bob";
        teamName1= "Backstreet Boys";
        teamName2= "Golden Boys";
        teamOwner1 = new TeamOwner(name1, realName1, 789, "Y8O8", id1);
        teamOwner2 = new TeamOwner(name2, realName2, 787, "Y8O9", id2);
        teamAway = new Team(teamName1, teamOwner1);
        teamHome = new Team(teamName2, teamOwner2);
        assetID = 222;
        field = new Field();
        field.setMyTeam(teamAway);
        budget11= 400;
        budget11= 500;
        totalBudget1= budget11+budget12;
        player1 = new Player(name1,name1,id1,"AAA",assetID,"Bla", new Date(1999,02,02));
        player2 = new Player(name1,name1,id1,"AAA",assetID+1,"Bla", new Date(1999,02,02));
        teamPlayers = new HashMap<>();
        coach = new Coach("Alon", "Nurd", 666,"Hihi", 20,"Bla", CoachRole.FirstAssistantCoach);
        teamManager = new TeamManager("Shir", "Shira", 333, "SSS", 333, teamAway,teamOwner1);
        refereeSec = new Referee("Tomos", "Tom", 567, "T5O6", RefereeType.Secondary);
        refereeMain = new Referee("Yomos", "Yom", 566, "Y5O6", RefereeType.Main);
        season = new Season(2020);
        season1 = new Season(2222);
        game = new Game(teamAway,teamHome,date, refereeMain,refereeSec,season);
        player = new Player("Polo","Apolo",333, "A3P3", 1,"Player", date);
        foul = new Foul(54, player);
        system = FootballManagmentSystem.getInstance();
        member = new Player("Halisi","Drakaries",313546,"HIHI",assetID+3,"BlaBla", new Date(1995,03,02));
    }




    @Test
    public void getId() {
        assertEquals(teamAway.getId(), teamAway.getId());

    }

    @Test
    public void isActive() {
        assertTrue(teamAway.isActive());
    }

    @Test
    public void getCurrentBudget() throws InactiveTeamException, UnauthorizedTeamOwnerException {
        teamAway.addBudgetActivity(teamOwner1,new Date(), GameIncome, budget11);
        teamAway.addBudgetActivity(teamOwner1,new Date(), GameIncome, budget12);
        int result = teamAway.getCurrentBudget();
        assertEquals(totalBudget1,result);
    }

    @Test
    public void calculatePlayerFootballRate() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        teamOwner1.addAssetToTeam(player1);
        teamOwner1.addAssetToTeam(player2);
        teamAway.calculatePlayerFootballRate();
    }



    @Test
    public void getTeamPlayers() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        teamOwner1.addAssetToTeam(player1);
        HashMap<Integer, IAsset> ans = teamAway.getTeamPlayers();
        IAsset result = (Player) ans.get(player1.getAssetID());
        boolean flag = false;
        if (player1.equals(result)){
            flag=true;
        }
        assertTrue(flag);
    }



    @Test
    public void setTeamPlayers() throws TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException {
        teamAway.setTeamPlayers(teamPlayers);
        HashMap<Integer, IAsset> ans = teamAway.getTeamPlayers();
        boolean flag = false;
        if (teamPlayers.equals(ans)){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void addAsset() throws InactiveTeamException, UnauthorizedTeamOwnerException {
        teamAway.addAsset(player1, player1);
        HashMap<Integer, IAsset> ans = teamAway.getTeamPlayers();
        IAsset result = (Player) ans.get(player1.getAssetID());
        boolean flag = false;
        if (player1.equals(result)){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void removeAssetFromTeam() throws InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        teamAway.addAsset(player1, player1);
        teamAway.removeAssetFromTeam(player1, player1);
        HashMap<Integer, IAsset> ans = teamAway.getTeamPlayers();
        IAsset result = (Player) ans.get(player1.getAssetID());
        boolean flag = false;
        if (player1.equals(result)){
            flag=true;
        }
        assertFalse(flag);
    }


    @Test (expected = InvalidTeamAssetException.class)
    public void removeAssetFromTeam1() throws InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        teamAway.removeAssetFromTeam(player1, player1);
    }


    @Test
    public void editAsset() throws UnauthorizedTeamOwnerException, InactiveTeamException, InvalidTeamAssetException {
        teamAway.addAsset(player1, player1);
        boolean flag = teamAway.editAsset(player1, player1, 100);
        assertTrue(flag);
    }

    @Test
    public void editAsset1() throws UnauthorizedTeamOwnerException, InactiveTeamException, InvalidTeamAssetException {
        teamAway.addAsset(player1, coach);
        boolean flag = teamAway.editAsset(player1, coach, 100);
        assertTrue(flag);
    }

    @Test
    public void editAsset2() throws UnauthorizedTeamOwnerException, InactiveTeamException, InvalidTeamAssetException {
        teamAway.addAsset(player1, field);
        boolean flag = teamAway.editAsset(player1, field, 100);
        assertTrue(flag);
    }

    @Test (expected = UnauthorizedTeamOwnerException.class)
    public void editAsset3() throws UnauthorizedTeamOwnerException, InactiveTeamException, InvalidTeamAssetException {
        teamAway.editAsset(coach, coach, 4);
    }

    @Test
    public void addNewTeamOwner() throws InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, UserIsNotThisKindOfMemberException, TeamOwnerWithNoTeamException {

        teamOwner1.assignNewTeamOwner(member);
        member = system.getMemberInstanceByKind(member.getName(), "Team Owner");
        boolean flag = false;
        if (member.equals(teamAway.getAllTeamOwners().get(1))){
            flag=true;
        }
        assertTrue(flag);    }

    @Test (expected = UserInformationException.class)
    public void addNewTeamOwner1() throws InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException {
        assertTrue(teamAway.addNewTeamOwner(teamOwner1, teamOwner2));
    }


    @Test
    public void addNewTeamManger() throws InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, TeamOwnerWithNoTeamException, UserIsNotThisKindOfMemberException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, UnauthorizedTeamManagerException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        Member member = new Player("Halisi","Drakaries",313546,"HIHI",assetID+3,"BlaBla", new Date(1995,03,02));
        teamOwner1.assignNewTeamManager(member, 200000);
        member = system.getMemberInstanceByKind(member.getName(), "Team Manager");
        boolean flag = false;
        if (member.equals(teamAway.getAllTeamManaers().get(0))){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void editManagerPermissions() throws InactiveTeamException, UserInformationException, UnauthorizedPageOwnerException, UnauthorizedTeamOwnerException, PersonalPageYetToBeCreatedException {
        teamAway.editManagerPermissions(teamOwner1, teamManager, "READ", true);
    }



    @Test
    public void getAllTeamOwners() {
    }

    @Test
    public void getAllTeamManaers() {
    }

    @Test
    public void addCoach() {
    }

    /*
    @Test
    public void createPersonalPage() throws InactiveTeamException, UnauthorizedTeamManagerException {
        teamAway.createPersonalPage(teamManager);
    }
    */

    @Test
    public void addContentToPersonalPage() {
    }

    @Test
    public void editPersonalPageProfile() {
    }

    @Test
    public void addPersonalPageEditor() {
    }

    @Test
    public void removePersonalPageEditor() {
    }

    @Test(expected = UnauthorizedTeamOwnerException.class)
    public void addBudgetActivity() throws UnauthorizedTeamOwnerException, InactiveTeamException {
        teamAway.addBudgetActivity(teamOwner2,new Date(), GameIncome, totalBudget1);
    }

    @Test
    public void changeTeamStatus() throws UnauthorizedTeamOwnerException, TeamCannotBeReopenException {
        teamAway.changeTeamStatus(teamOwner1, TeamStatus.Close);
        boolean flag = false;
        if (TeamStatus.Close.equals(teamAway.getStatus())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void notifyTeam() {
    }

    @Test
    public void equals() {
    }

    @Test
    public void addToGamesHistory() {
        teamAway.addToGamesHistory(teamHome, game);
        LinkedList<Game> ans = teamAway.getGameHistoryWithOtherTeam(teamHome);
        Game result = ans.get(0);
        boolean flag = false;
        if (game.equals(result)){
            flag=true;
        }
        assertTrue(flag);

    }

    @Test
    public void getGameHistoryWithOtherTeam() {
    }


    @Test
    public void getPlayersFootballRate() {
    }

    @Test
    public void isClosed() {
        teamAway.setClosed(true);
        assertTrue(teamAway.isClosed());
    }

    @Test
    public void setClosed() {
    }

    @Test
    public void getSeasons() {
        List<Season> seasons = new LinkedList<>();
        seasons.add(season);
        seasons.add(season1);
        teamAway.setSeasons(seasons);
        assertEquals(2, teamAway.getSeasons().size());
    }

    @Test
    public void setSeasons() {
    }

    @Test
    public void getCurrentSeason() {
        teamAway.setCurrentSeason(season1);
        boolean flag = false;
        if (season1.equals(teamAway.getCurrentSeason())){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void setCurrentSeason() {
    }


    @Test
    public void setInfo() {
    }

    @Test
    public void getOwner() {
    }

    @Test
    public void setOwner() {
    }

    @Test
    public void getStatus() {
    }

    @Test
    public void setStatus() {
    }

    @Test
    public void setScore() {
    }

    @Test
    public void getScore() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void getControlBudget() {
    }


    /*
    @Test
    public void removeTeamOwner() throws InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, TeamOwnerWithNoTeamException, UserIsNotThisKindOfMemberException {
        teamOwner1.assignNewTeamOwner(member);
        member = system.getMemberInstanceByKind(member.getName(), "Team Owner");
        int size = teamAway.getAllTeamOwners().size();
        teamAway.removeTeamOwner((TeamOwner)member,teamOwner1);
        int size1 = teamAway.getAllTeamOwners().size();
        boolean flag = false;
        if (size != (size1)){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void removeTeamManager() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UserIsNotThisKindOfMemberException {
        teamOwner2.assignNewTeamManager(member,20);
        member = system.getMemberInstanceByKind(member.getName(), "Team Manager");
        int size = teamHome.getAllTeamManaers().size();
        teamHome.removeTeamManager(teamOwner2,member);
        boolean flag = false;
        if (size != (teamHome.getAllTeamManaers().size())){
            flag=true;
        }
        assertTrue(flag);
    }

      @Test
    public void getInfo() {
        PersonalInfo personalInfo;
        teamAway.setInfo();
        boolean flag = false;
        if (season1.equals(teamAway.getCurrentSeason())){
            flag=true;
        }
        assertTrue(flag);
    }



//    @Test
//    public void removeTeamOwner() throws InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException, MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, TeamOwnerWithNoTeamException, UserIsNotThisKindOfMemberException {
////        system = FootballManagmentSystem.getInstance();
////        member = new Player("Halisi","Drakaries",313546,"HIHI",assetID+3,"BlaBla", new Date(1995,03,02));
//        teamOwner1.assignNewTeamOwner(member);
//        member = system.getMemberInstanceByKind(member.getName(), "Team Owner");
//        int size = teamAway.getAllTeamOwners().size();
//        teamAway.removeTeamOwner((TeamOwner)member,teamOwner1);
//        boolean flag = false;
//        if (size != (teamAway.getAllTeamOwners().size())){
//            flag=true;
//        }
//        assertTrue(flag);
//    }


    */


}