package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;

public class SeasonTeamsDAL implements DAL<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> {

    /**
     * T - objectToInsert - key = pair (key = season ID , value = teamID)
     * Value = teamScore
     * E - objectIdentifier - key = pair (key = season ID , value = teamID)
     */

    Connection connection = null;

    @Override
    public boolean insert(Pair<Pair<Integer, Integer>, Integer> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        return false;
    }

    @Override
    public boolean update(Pair<Pair<Integer, Integer>, Integer> objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public Pair<Pair<Integer, Integer>, Integer> select(Pair<Integer, Integer> objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Pair<Integer, Integer> objectIdentifier) {
        return false;
    }
}
