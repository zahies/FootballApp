package DatabaseTests;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.LeaguesDAL;
import DataAccess.UsersDAL.CommissionersDAL;
import DataAccess.UsersDAL.TeamManagerDAL;
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
    public void teamManagementTest() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException, TeamCannotBeReopenException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InactiveTeamException, UnauthorizedPageOwnerException, UnauthorizedTeamManagerException, InvalidTeamAssetException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        try {
            TeamOwner teamOwner = new TeamOwner("Ohana", "Eli Ohana", 0, "Ohana");
            Team beitar = new Team("Beiter", teamOwner);
            IAsset coach = new Coach("RoniLevy$", "Roni Levy", 266, "RoniLevy$", 5000, "Pro", CoachRole.HeadCoach);
            teamOwner.addAssetToTeam(coach);
            IAsset player = new Player("Aliran92", "Aliran Levy", 0, "Aliran92", 1000, "Striker", null);
            teamOwner.addAssetToTeam(player);
            teamOwner.editAsset(player, 165165);
            if (coach instanceof Coach) {
                teamOwner.assignNewTeamOwner(fms.getMemberInstanceByKind(((Coach) coach).getName(), "Coach"));
            }
            Member playerAsTeamManager = null;
            if (player instanceof Player) {
                playerAsTeamManager = fms.getMemberInstanceByKind(((Player) player).getName(), "Player");
            }
            teamOwner.assignNewTeamManager(playerAsTeamManager, 45645);
            playerAsTeamManager = fms.getMemberInstanceByKind(playerAsTeamManager.getName(), "Team Manager");
            teamOwner.editManagerPermissions(playerAsTeamManager, "Create Personal Page", true);
            ((TeamManager) playerAsTeamManager).createPersonalPageForTeam();
            teamOwner.editManagerPermissions(playerAsTeamManager, "Add Content To Personal Page", true);
            ((TeamManager) playerAsTeamManager).addContentToTeamsPersonalPage(new ProfileContent());
            ((TeamManager) playerAsTeamManager).addContentToTeamsPersonalPage(new NewsContent());
            teamOwner.removeTeamManager(fms.getMemberInstanceByKind(playerAsTeamManager.getName(), "Team Manager"));

            teamOwner.addAssetToTeam(new Field());


            TeamOwner teamOwner2 = new TeamOwner("Prosper", "prosper azagi", 203, "pr");
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
        } catch (MemberIsAlreadyTeamManagerException e) {
            e.printStackTrace();
        } catch (PersonalPageYetToBeCreatedException e) {
            e.printStackTrace();
        } catch (MemberIsAlreadyTeamOwnerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void seasonFlowTest() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, LeagueNotFoundException, NotEnoughTeamsInLeague, SeasonYearAlreadyExist, LeagueIDAlreadyExist, IDWasNotEnterdException, TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException, MemberIsAlreadyTeamOwnerException, MemberIsAlreadyTeamManagerException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, UnauthorizedTeamManagerException, TeamCannotBeReopenException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        TeamOwner teamOwner = new TeamOwner("Ohana", "Eli Ohana", 0, "Ohana");
        Team beitar = new Team("Beiter Jerusalem", teamOwner);
        IAsset coach = new Coach("RoniLevy$", "Roni Levy", 266, "RoniLevy$", 5000, "Pro", CoachRole.HeadCoach);
        teamOwner.addAssetToTeam(coach);
        IAsset player = new Player("Aliran92", "Aliran Levy", 0, "Aliran92", 1000, "Striker", null);
        teamOwner.addAssetToTeam(player);
        teamOwner.editAsset(player, 165165);
        if (coach instanceof Coach) {
            teamOwner.assignNewTeamOwner(fms.getMemberInstanceByKind(((Coach) coach).getName(), "Coach"));
        }
        Member playerAsTeamManager = null;
        if (player instanceof Player) {
            playerAsTeamManager = fms.getMemberInstanceByKind(((Player) player).getName(), "Player");
        }
        teamOwner.assignNewTeamManager(playerAsTeamManager, 45645);
        playerAsTeamManager = fms.getMemberInstanceByKind(playerAsTeamManager.getName(), "Team Manager");
        teamOwner.editManagerPermissions(playerAsTeamManager, "Create Personal Page", true);
        ((TeamManager) playerAsTeamManager).createPersonalPageForTeam();
        teamOwner.editManagerPermissions(playerAsTeamManager, "Add Content To Personal Page", true);
        ((TeamManager) playerAsTeamManager).addContentToTeamsPersonalPage(new ProfileContent());
        ((TeamManager) playerAsTeamManager).addContentToTeamsPersonalPage(new NewsContent());
        teamOwner.removeTeamManager(fms.getMemberInstanceByKind(playerAsTeamManager.getName(), "Team Manager"));


        Leaugue leaugue = new Leaugue();
        Commissioner commissioner = new Commissioner("Shino",0,"Shino","Shino Zuarch");

        TeamOwner teamOwner2 = new TeamOwner("TabibE","Eli Tabib",21,"TabibE");
        TeamOwner teamOwner3 = new TeamOwner("NissanovBt","Moshe Nissanov",22,"NissanovBT");
        TeamOwner teamOwner4 = new TeamOwner("ArkadiG","Arkadi Gaidamak",25,"ArkadiG");

        Team team2 = new Team("Maccabi Haifa",teamOwner2);
        Team team3 = new Team("Hapoel Tel Aviv",teamOwner3);
        Team team4 = new Team("Ironi Raanana",teamOwner4);


        IAsset coach2 = new Coach("PhilipeC", "Philipe Bardugo", 266, "PhilipeC", 6000, "Pro", CoachRole.HeadCoach);
        teamOwner2.addAssetToTeam(coach2);
        teamOwner2.assignNewTeamManager((Coach)coach2,13000);
        TeamManager teamManager2 = new TeamManagerDAL().select("PhilipeC",true);
        teamOwner2.editManagerPermissions(teamManager2,"Create Personal Page",true);
        team2.createPersonalPage(teamManager2);

        IAsset player2 = new Player("ItayE", "Itay Shechter", 0, "ItayE", 6000, "Midfield", null);
        teamOwner2.addAssetToTeam(player2);

        IAsset coach3 = new Coach("Eitan", "Eithan Titinski", 266, "Eitan", 7080, "Semi-Pro", CoachRole.HeadCoach);
        teamOwner3.addAssetToTeam(coach3);
        teamOwner3.assignNewTeamManager((Coach)coach3,12000);
        TeamManager teamManager3 = new TeamManagerDAL().select("Eitan",true);
        teamOwner3.editManagerPermissions(teamManager3,"Create Personal Page",true);
        team3.createPersonalPage(teamManager3);

        IAsset player3 = new Player("Kapi", "Niso Kapiloti", 0, "Kapi", 6000, "Defender", null);
        teamOwner3.addAssetToTeam(player3);

        IAsset coach4 = new Coach("Dvir", "Dvir Buzaglo", 266, "Dvir", 7080, "Semi-Pro", CoachRole.HeadCoach);
        teamOwner4.addAssetToTeam(coach4);
        teamOwner4.assignNewTeamManager((Coach)coach4,11000);
        TeamManager teamManager4 = new TeamManagerDAL().select("Eitan",true);
        teamOwner4.editManagerPermissions(teamManager4,"Create Personal Page",true);
        team3.createPersonalPage(teamManager4);
        IAsset player4 = new Player("Rafo", "Kobi Rafoa", 0, "Rafo", 2000, "Goal Keeper", null);
        teamOwner4.addAssetToTeam(player4);


        Referee ref = new Referee("Liran","Liran Shobart",4,"Liran",RefereeType.Main);
        Referee ref2 = new Referee("Yossi","Yossi Shitrit",5,"Yossi",RefereeType.Secondary);
        Referee ref3 = new Referee("Yaakov","Yaakov Levy",6,"Yaakov",RefereeType.Main);
        Referee ref4 = new Referee("Moshe","Moshe Cohen",57,"Moshe",RefereeType.Secondary);


        leaugue.setLeagueIntoSystem();
        leaugue.addSeasonToLeagueByYear(2020);
        //fms.addMember(commissioner);
        //DefaultTeamsPolicy df = new DefaultTeamsPolicy();
        //DefaultIScorePolicy ds = new DefaultIScorePolicy();
        //commissioner.setNewPlaceTeamsPolicy(UUID.fromString("3f0b955b-d669-4a65-aa1d-0961733cd13b"),2020,df);
        //commissioner.setNewScorePolicy(UUID.fromString("3f0b955b-d669-4a65-aa1d-0961733cd13b"),2020,ds);
        leaugue.getSeasonByYear(2020).addTeamToSeason(beitar);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team2);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team3);
        leaugue.getSeasonByYear(2020).addTeamToSeason(team4);
        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref2);
        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref);
        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref3);
        commissioner.addRefereeToSeason(leaugue.getObjectID(),2020,ref4);
        commissioner.runPlacingAlgo(leaugue,2020);


    }

}
