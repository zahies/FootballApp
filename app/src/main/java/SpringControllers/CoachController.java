package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UserInformationDAL.PersonalPageContentDAL;
import DataAccess.UsersDAL.CoachesDAL;
import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.Coach;
import Domain.Users.Member;
import FootballExceptions.*;

import java.sql.SQLException;

public class CoachController extends MemberController {

    public boolean addContentToPage(String username, String contString) {
        boolean flag = false;
        try {
            Coach coach = (Coach)new CoachesDAL().select(username,true);
            APersonalPageContent cont = (APersonalPageContent)new PersonalPageContentDAL().select(contString,true);
            coach.addContentToPersonalPage(cont);
            flag=true;
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
        return flag;
    }

    public boolean EditPage(String username, String title, String val) {
        boolean flag = false;
        try {
            Coach coach = (Coach)new CoachesDAL().select(username,true);
            coach.editProfile(title, val);
            flag =true;
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
        boolean flag = false;
        try {
            Coach coach = (Coach)new CoachesDAL().select(username,true);
            coach.changeUserName(newUserName);
            flag = true;
        } catch (UserInformationException | NoConnectionException | SQLException | NoPermissionException | UserIsNotThisKindOfMemberException e) {
            return false;
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean createPersonalPage(String username) throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Coach coach = (Coach)new CoachesDAL().select(username,true);
        return coach.createPersonalPage();
    }
}
