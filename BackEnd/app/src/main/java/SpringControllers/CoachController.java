package SpringControllers;

import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.Coach;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;
import FootballExceptions.UserInformationException;

public class CoachController extends MemberController {

    public boolean addContentToPage(Coach coach, APersonalPageContent cont) {
        try {
            coach.addContentToPersonalPage(cont);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            return false;
        }
        return true;
    }

    public void EditPage(Coach coach, String title, String val) {
        try {
            coach.editProfile(title, val);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean changeUserName(Coach coach, String newUserName) {
        try {
            return coach.changeUserName(newUserName);
        } catch (UserInformationException e) {
            return false;
        }
    }

    public boolean createPersonalPage(Coach coach) {
        return coach.createPersonalPage();
    }
}
