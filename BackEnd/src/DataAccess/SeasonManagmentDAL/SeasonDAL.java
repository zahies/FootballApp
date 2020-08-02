package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Season;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.SQLException;

public class SeasonDAL implements DAL<Season, Integer> {
    @Override
    public boolean insert(Season objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        return false;
    }

    @Override
    public boolean update(Season objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public Season select(Integer objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
