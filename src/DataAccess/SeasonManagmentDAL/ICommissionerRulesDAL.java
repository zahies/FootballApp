package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.DefaultCommissionerRule;
import Domain.SeasonManagment.ICommissionerRule;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ICommissionerRulesDAL implements DAL<ICommissionerRule,String> {


    @Override
    public boolean insert(ICommissionerRule objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO commissioner_rules (ObjectID, Description) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setString(2,objectToInsert.getDescription());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(ICommissionerRule objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "UPDATE commissioner_rules SET Description=? WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToUpdate.getDescription());
        preparedStatement.setString(2,objectToUpdate.getObjectID().toString());
        int ans = preparedStatement.executeUpdate();

        return ans==1;
    }

    @Override
    public ICommissionerRule select(String objectIdentifier, boolean bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException{
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="SELECT * FROM commissioner_rules WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        //return new DefaultCommissionerRule(UUID.fromString(objectIdentifier),rs.getString("Description"));
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
