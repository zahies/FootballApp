package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Season;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class LeaguesDAL implements DAL<Leaugue,String> {
    Connection connection =null;

    @Override
    public boolean insert(Leaugue objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection =connect();

        String statement ="INSERT INTO leauge (ObjectID) VALUES (?);";
        PreparedStatement preparedStatement =connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getID().toString());
        preparedStatement.execute();

        HashMap<Integer, Season> seasonHashMap = objectToInsert.getSeasons();
        for (int key: seasonHashMap.keySet()) {
            new LeagueSeasonsDAL().insert(new Pair<>(new Pair<>(objectToInsert.getID().toString(),key),seasonHashMap.get(key).getObjectID().toString()));
        }
        connection.close();

        return true;
    }

    @Override
    public boolean update(Leaugue objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection =connect();

        HashMap<Integer, Season> seasonHashMap = objectToUpdate.getSeasons();
        for (int key: seasonHashMap.keySet()) {
            new LeagueSeasonsDAL().update(new Pair<>(new Pair<>(objectToUpdate.getID().toString(),key),seasonHashMap.get(key).getObjectID().toString()));
        }
        connection.close();

        return true;
    }

    @Override
    public Leaugue select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
