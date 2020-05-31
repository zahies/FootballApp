package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.MembersDAL;
import Domain.FootballManagmentSystem;
import Domain.Users.Member;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;

public class MemberController {





    public boolean logOut(String username) throws UserInformationException, SQLException, NoPermissionException, NoConnectionException, UserIsNotThisKindOfMemberException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, EmptyPersonalPageException {
        boolean flag = false;
        Member member = (Member) new MembersDAL().select(username,true);
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        member.logOut();
        flag = true;
        return flag;
    }


    public void setViaMail(String Username, String mail) throws NoPermissionException, EmptyPersonalPageException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        Member member = new MembersDAL().select(Username,true);
        member.setAlertViaMail(true);
        member.setMailAddress(mail);
    }
}
