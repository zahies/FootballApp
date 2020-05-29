package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UserInformationDAL.PersonalPageContentDAL;
import DataAccess.UsersDAL.PlayersDAL;
import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.Coach;
import Domain.Users.Member;
import Domain.Users.Player;
import FootballExceptions.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.SQLException;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlayerController extends MemberController {


    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }


    public boolean addContentToPage(String username, String contstring) {
        try {
            Player player = (Player) new PlayersDAL().select(username);
            APersonalPageContent cont = (APersonalPageContent) new PersonalPageContentDAL().select(contstring);
            player.addContentToPersonalPage(cont);
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            return false;
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean EditPage(String username, String title, String val) {
        boolean flag = false;
        try {
            Player player = (Player) new PlayersDAL().select(username);
            player.editProfile(title, val);
            flag = true;
        } catch (UnauthorizedPageOwnerException e) {
            System.out.println(e.getMessage());
        } catch (PersonalPageYetToBeCreatedException e) {
            System.out.println(e.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean changeUserName(String username, String newUserName) {
        try {
            Player player = (Player) new PlayersDAL().select(username);
            return player.changeUserName(newUserName);
        } catch (UserInformationException e) {
            return false;
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createPersonalPage(String username) {
        Player player = null;
        try {
            player = (Player) new PlayersDAL().select(username);
            return player.createPersonalPage();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
