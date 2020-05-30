package DatabaseTests.UsersDALTest;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import Domain.Users.PersonalInfo;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Test;

import java.sql.SQLException;

public class PersonalPageDALTest {

    @Test
    public void test() throws NoPermissionException, EmptyPersonalPageException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        PersonalInfo personalInfo = new PersonalPagesDAL().select(28745299,true);
        System.out.println("FAS");
    }
}
