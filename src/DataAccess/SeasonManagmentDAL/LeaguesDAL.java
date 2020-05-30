package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Season;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaguesDAL implements DAL<Leaugue,String> {


    @Override
    public boolean insert(Leaugue objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

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
        Connection connection = MySQLConnector.getInstance().connect();

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

    public List<Leaugue> selectAll() throws NoConnectionException, SQLException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException {
        List<Leaugue> allLeagus = new ArrayList<>();
        Connection connection =MySQLConnector.getInstance().connect();

        String statement ="SELECT ObjectID FROM leauge";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            allLeagus.add(this.select(rs.getString("ObjectID"),true));
        }
        connection.close();
        return allLeagus;
    }
}
