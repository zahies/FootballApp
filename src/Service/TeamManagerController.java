package Service;

import Domain.PersonalPages.APersonalPageContent;
import Domain.SeasonManagment.IAsset;
import Domain.Users.TeamManager;
import FootballExceptions.*;

public class TeamManagerController extends MemberController {

    /**
     * all Func in this controller are all according to UC 7.1 - team manager can do what team owner let him
     */

    public boolean hireCoach(TeamManager teamManager, IAsset newCoach) {
        try {
            return teamManager.hireCoach(newCoach);
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean createPersonalPageForTeam(TeamManager teamManager) {
        try {
            return teamManager.createPersonalPageForTeam();
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addContentToTeamsPersonalPage(TeamManager teamManager, APersonalPageContent content) {
        try {
            return teamManager.addContentToTeamsPersonalPage(content);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean editProfileOnPersonalPage(TeamManager teamManager, String title, String val) {
        try {
            return teamManager.editProfileOnPersonalPage(title, val);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (UnauthorizedTeamManagerException e) {
            System.out.println(e.getMessage());
        } catch (InactiveTeamException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
