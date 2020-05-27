package IntegrationTests;

import Domain.PersonalPages.NewsContent;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Field;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;
import Domain.Users.*;
import FootballExceptions.*;
import Domain.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TeamManagmentTests {
    @Test
    public  void checkManagment() {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        try {
            TeamOwner teamOwner = new TeamOwner("Moshe", "Moshe Hogeg", 203, "#123");
            Team beitar = new Team("Beiter", teamOwner);
            IAsset coach = new Coach("Roni", "roni levy", 266, "dsds", 5000, "fsdf", CoachRole.HeadCoach);
            teamOwner.addAssetToTeam(coach);
            IAsset player = new Player("CR7", "Ronaldo", 0, "sdasd", 1000, "Striker", null);
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
            assertTrue(beitar.getStatus()==TeamStatus.Close);
            //teamOwner.changeTeamStatus(TeamStatus.Active);
            //assertTrue(beitar.getStatus()==TeamStatus.Active);
            teamOwner.addAssetToTeam(new Field());
            assertEquals(1,beitar.getTeamPlayers().size());
            assertEquals(0,beitar.getAllTeamManaers().size());

            TeamOwner teamOwner2 = new TeamOwner("prosper", "prosper azagi", 203, "#123");
            Team team = new Team("Ironi Nir Ramat-Hasharon", teamOwner2);
            teamOwner2.addAssetToTeam(player);

            assertEquals(1,team.getTeamPlayers().size());
            assertEquals("Ironi Nir Ramat-Hasharon",player.getMyTeam().getName());

            teamOwner2.addAssetToTeam(coach);
            assertEquals(2,team.getTeamPlayers().size()+1);
            assertEquals("Ironi Nir Ramat-Hasharon",coach.getMyTeam().getName());

        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {

        } catch (MemberIsAlreadyTeamManagerException e) {
            e.printStackTrace();
        } catch (TeamOwnerWithNoTeamException e) {
            e.printStackTrace();
        } catch (UnauthorizedTeamOwnerException e) {
            e.printStackTrace();
        } catch (
                InvalidTeamAssetException e) {
            e.printStackTrace();
        } catch (
                MemberIsAlreadyTeamOwnerException e) {
            e.printStackTrace();
        } catch (
                UnauthorizedPageOwnerException e) {
            e.printStackTrace();
        } catch (
                PersonalPageYetToBeCreatedException e) {
            e.printStackTrace();
        } catch (
                UnauthorizedTeamManagerException e) {
            e.printStackTrace();
        } catch (TeamCannotBeReopenException e) {
            e.printStackTrace();
        }
    }
}
