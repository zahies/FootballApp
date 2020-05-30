package DatabaseTests.UsersDALTest;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import DataAccess.UsersDAL.FansDAL;
import Domain.SeasonManagment.Team;
import Domain.Users.Fan;
import Domain.Users.PersonalInfo;
import FootballExceptions.*;
import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FansDALTest {

    @Test
    public void test() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException, AlreadyFollowThisPageException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, EmptyPersonalPageException {
        Fan fan = new FansDAL().select("Ozi9",true);
        fan.turnAlertForPersonalPageOn(new PersonalPagesDAL().select(28745299,true));

    }
}
