package Service;

import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.Coach;
import Domain.Users.Member;
import Domain.Users.Player;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;
import FootballExceptions.UserInformationException;

public class PlayerController extends Member {
    public boolean addContentToPage(Player player, APersonalPageContent cont) {
        try {
            player.addContentToPersonalPage(cont);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            return false;
        }
        return true;
    }

    public void EditPage(Player player, String title, String val) {
        try {
            player.editProfile(title, val);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean changeUserName(Player player, String newUserName) {
        try {
            return player.changeUserName(newUserName);
        } catch (UserInformationException e) {
            return false;
        }

    }

    public boolean createPersonalPage(Player player) {
        return player.createPersonalPage();
    }
}
