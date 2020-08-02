package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import Domain.Users.PersonalInfo;
import FootballExceptions.UserInformationException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonalPagesDAL implements DAL<PersonalInfo, Integer> {
    Connection connection = null;

    @Override
    public boolean insert(PersonalInfo objectToInsert) throws SQLException, NoConnectionException {

        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }

        String statement = "INSERT INTO personalpages (idPersonalPages, title, profileContent) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getPageID());
        preparedStatement.setString(2, objectToInsert.getPageTitle());
        preparedStatement.setInt(3, objectToInsert.getProfile().getObjectID());
        preparedStatement.execute();
        connection.close();

        return true;
    }

    @Override
    public boolean update(PersonalInfo objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException {
        return false;
    }

    @Override
    public PersonalInfo select(Integer objectIdentifier) throws SQLException, UserInformationException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
