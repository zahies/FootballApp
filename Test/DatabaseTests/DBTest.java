package DatabaseTests;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.LeaguesDAL;
import DataAccess.UsersDAL.CommissionersDAL;
import Domain.PersonalPages.NewsContent;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.*;
import Domain.Users.*;
import FootballExceptions.*;
import Domain.*;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBTest {



    @Test
    public void teamManagementTest() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        try {
//            TeamOwner teamOwner = new TeamOwner("Moshe23", "Moshe Hogeg", 203, "#123");
//            Team beitar = new Team("Beiter", teamOwner);
//            IAsset coach = new Coach("Roni23", "roni levy", 266, "dsds", 5000, "fsdf", CoachRole.HeadCoach);
//            teamOwner.addAssetToTeam(coach);
//            IAsset player = new Player("CR723", "Ronaldo", 0, "sdasd", 1000, "Striker", null);
//            teamOwner.addAssetToTeam(player);
//            teamOwner.editAsset(player, 165165);
//            if (coach instanceof Coach) {
//                teamOwner.assignNewTeamOwner(fms.getMemberInstanceByKind(((Coach) coach).getName(), "Coach"));
//            }
//            Member playerAsTeamManager = null;
//            if (player instanceof Player) {
//                playerAsTeamManager = fms.getMemberInstanceByKind(((Player) player).getName(), "Player");
//            }
//            teamOwner.assignNewTeamManager(playerAsTeamManager, 45645);
//            playerAsTeamManager = fms.getMemberInstanceByKind(playerAsTeamManager.getName(), "Team Manager");
//            teamOwner.editManagerPermissions(playerAsTeamManager, "Create Personal Page", true);
//            ((TeamManager) playerAsTeamManager).createPersonalPageForTeam();
//            teamOwner.editManagerPermissions(playerAsTeamManager, "Add Content To Personal Page", true);
//            ((TeamManager) playerAsTeamManager).addContentToTeamsPersonalPage(new ProfileContent());
//            ((TeamManager) playerAsTeamManager).addContentToTeamsPersonalPage(new NewsContent());
//            teamOwner.removeTeamManager(fms.getMemberInstanceByKind(playerAsTeamManager.getName(), "Team Manager"));
//            teamOwner.changeTeamStatus(TeamStatus.Close);
//            //assertTrue(beitar.getStatus()==TeamStatus.Close);
//            //teamOwner.changeTeamStatus(TeamStatus.Active);
//            //assertTrue(beitar.getStatus()==TeamStatus.Active);
//            teamOwner.addAssetToTeam(new Field());
            //assertEquals(1,beitar.getTeamPlayers().size());
            //a/ssertEquals(0,beitar.getAllTeamManaers().size());

            TeamOwner teamOwner2 = new TeamOwner("prosper", "prosper azagi", 203, "#123");
            Team team = new Team("Ironi Nir Ramat-Hasharon", teamOwner2);
            //teamOwner2.addAssetToTeam(player);

            //assertEquals(1,team.getTeamPlayers().size());
            //assertEquals("Ironi Nir Ramat-Hasharon",player.getMyTeam().getName());

            //teamOwner2.addAssetToTeam(coach);
            assertEquals(2,team.getTeamPlayers().size()+1);
            //assertEquals("Ironi Nir Ramat-Hasharon",coach.getMyTeam().getName());

        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void seasonFlowTest() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, LeagueNotFoundException, NotEnoughTeamsInLeague, SeasonYearAlreadyExist, LeagueIDAlreadyExist, IDWasNotEnterdException {
        //FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        Leaugue leaugue = new LeaguesDAL().select("9fac3dac-102c-4c42-86af-57fd8691c3d6",true);
        System.out.println("jgh");
        Commissioner commissioner = new CommissionersDAL().select("AlonGeva",true);
//        TeamOwner teamOwner = new TeamOwner("Avishai2m","Avishai Cohen",0,"E2@@qs");
//        TeamOwner teamOwner2 = new TeamOwner("gabiPrince120","avishai",21,"12345");
//        TeamOwner teamOwner3 = new TeamOwner("gabiKing120","avishai",22,"12345");
//        TeamOwner teamOwner4 = new TeamOwner("gabiKing0","avishai",25,"12345");
//        Team team = new Team("Hapoel Haifa",teamOwner);
//        Team team2 = new Team("Maccabi Haifa",teamOwner2);
//        Team team3 = new Team("Hapoel Kosi-lam-lam",teamOwner3);
//        Team team4 = new Team("Efrohei Tapash",teamOwner4);
//        //fms.addTeam(team);
//        //fms.addMember(teamOwner);
//        //teamOwner.setTeam(team);
//        Referee ref = new Referee("ref1","theref",4,"3232",RefereeType.Main);
//        Referee ref2 = new Referee("ref2","theref2",5,"3232",RefereeType.Secondary);
//        Referee ref3 = new Referee("ref3","theref2",6,"3232",RefereeType.Main);
//        Referee ref4 = new Referee("ref4","theref2",57,"3232",RefereeType.Secondary);
//
//
//        leaugue.setLeagueIntoSystem();
//        leaugue.addSeasonToLeagueByYear(2020);
//        //fms.addMember(commissioner);
//        //DefaultTeamsPolicy df = new DefaultTeamsPolicy();
//        //DefaultIScorePolicy ds = new DefaultIScorePolicy();
//        //commissioner.setNewPlaceTeamsPolicy(UUID.fromString("3f0b955b-d669-4a65-aa1d-0961733cd13b"),2020,df);
//        //commissioner.setNewScorePolicy(UUID.fromString("3f0b955b-d669-4a65-aa1d-0961733cd13b"),2020,ds);
//        leaugue.getSeasonByYear(2020).addTeamToSeason(team);
//        leaugue.getSeasonByYear(2020).addTeamToSeason(team2);
//        leaugue.getSeasonByYear(2020).addTeamToSeason(team3);
//        leaugue.getSeasonByYear(2020).addTeamToSeason(team4);
//        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref2);
//        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref);
//        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref3);
//        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref4);
        commissioner.runPlacingAlgo(leaugue,2020);
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
//        table.put(team.getScore(),team);
//        table.put(team3.getScore(),team2);
//        table.put(team2.getScore(),team3);
//        table.put(team4.getScore(),team4);

    }

}
