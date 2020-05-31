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
    public void teamManagementTest() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException, TeamCannotBeReopenException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InactiveTeamException, UnauthorizedPageOwnerException, UnauthorizedTeamManagerException, InvalidTeamAssetException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        try {
            TeamOwner teamOwner = new TeamOwner("Ohana", "Eli Ohana", 0, "qweasd123");
            Team beitar = new Team("Beiter", teamOwner);
            IAsset coach = new Coach("RoniLevy$", "Roni Levy", 266, "dain12@", 5000, "Pro", CoachRole.HeadCoach);
            teamOwner.addAssetToTeam(coach);
            IAsset player = new Player("Aliran92", "Aliran Levy", 0, "d2@#", 1000, "Striker", null);
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
            teamOwner.changeTeamStatus(TeamStatus.Close);
            //assertTrue(beitar.getStatus()==TeamStatus.Close);
            //teamOwner.changeTeamStatus(TeamStatus.Active);
            //assertTrue(beitar.getStatus()==TeamStatus.Active);
            teamOwner.addAssetToTeam(new Field());
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
        TeamOwner teamOwner = new TeamOwner("Ohana", "Eli Ohana", 0, "qweasd123");
        Team beitar = new Team("Beiter Jerusalem", teamOwner);
        IAsset coach = new Coach("RoniLevy$", "Roni Levy", 266, "dain12@", 5000, "Pro", CoachRole.HeadCoach);
        teamOwner.addAssetToTeam(coach);
        IAsset player = new Player("Aliran92", "Aliran Levy", 0, "d2@#", 1000, "Striker", null);
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

        //assertTrue(beitar.getStatus()==TeamStatus.Close);
        //teamOwner.changeTeamStatus(TeamStatus.Active);
        //assertTrue(beitar.getStatus()==TeamStatus.Active);
        teamOwner.addAssetToTeam(new Field());
        //assertEquals(1,beitar.getTeamPlayers().size());
        //a/ssertEquals(0,beitar.getAllTeamManaers().size());
        Leaugue leaugue = new Leaugue();
        Commissioner commissioner = new Commissioner("Shino",0,"132ad$^12","Shino Zuarch");

        TeamOwner teamOwner2 = new TeamOwner("TabibE","Eli Tabib",21,"iudshfi");
        TeamOwner teamOwner3 = new TeamOwner("NissanovBt","Moshe Nissanov",22,"asdqwe1");
        TeamOwner teamOwner4 = new TeamOwner("ArkadiG","Arkadi Gaidamak",25,"qwe123");

        Team team2 = new Team("Maccabi Haifa",teamOwner2);
        Team team3 = new Team("Hapoel Tel Aviv",teamOwner3);
        Team team4 = new Team("Ironi Raanana",teamOwner4);
        //fms.addTeam(team);
        //fms.addMember(teamOwner);
        //teamOwner.setTeam(team);

        IAsset coach2 = new Coach("PhilipeC", "Philipe Bardugo", 266, "asd159", 6000, "Pro", CoachRole.HeadCoach);
        teamOwner2.addAssetToTeam(coach2);
        IAsset player2 = new Player("ItayE", "Itay Shechter", 0, "d2@89#", 6000, "Midfield", null);
        teamOwner2.addAssetToTeam(player2);
        if (coach2 instanceof Coach) {
            teamOwner2.assignNewTeamOwner(fms.getMemberInstanceByKind(((Coach) coach2).getName(), "Coach"));
        }
        Member playerAsTeamManager2 = null;
        if (player2 instanceof Player) {
            playerAsTeamManager2 = fms.getMemberInstanceByKind(((Player) player2).getName(), "Player");
        }
        teamOwner2.assignNewTeamManager(playerAsTeamManager2, 45645);

        IAsset coach3 = new Coach("Eitan", "Eithan Titinski", 266, "asdx59", 7080, "Semi-Pro", CoachRole.HeadCoach);
        teamOwner3.addAssetToTeam(coach3);
        IAsset player3 = new Player("Kapi", "Niso Kapiloti", 0, "dasdzxc", 6000, "Defender", null);
        teamOwner3.addAssetToTeam(player3);

        IAsset coach4 = new Coach("Dvir", "Dvir Buzaglo", 266, "qweasd", 7080, "Semi-Pro", CoachRole.HeadCoach);
        teamOwner4.addAssetToTeam(coach4);
        IAsset player4 = new Player("Rafo", "Kobi Rafoa", 0, "789qwe", 2000, "Goal Keeper", null);
        teamOwner4.addAssetToTeam(player4);


        Referee ref = new Referee("Liran","Liran Shobart",4,"215asd",RefereeType.Main);
        Referee ref2 = new Referee("Yossi","Yossi Shitrit",5,"zxcasd",RefereeType.Secondary);
        Referee ref3 = new Referee("Yaakov","Yaakov Levy",6,"asdjkl",RefereeType.Main);
        Referee ref4 = new Referee("Moshe","Moshe Cohen",57,"rytvb",RefereeType.Secondary);


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
