package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.DefaultIScorePolicy;
import Domain.SeasonManagment.IScorePolicy;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ScorePoliciesDAL implements DAL<IScorePolicy,String> {


    @Override
    public boolean insert(IScorePolicy objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "INSERT INTO score_policies (ObjectID, winValue, loseValue, drawValue) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getId().toString());
        preparedStatement.setInt(2,objectToInsert.winVal());
        preparedStatement.setInt(3,objectToInsert.looseVal());
        preparedStatement.setInt(4,objectToInsert.drowVal());
        preparedStatement.execute();


        return true;
    }

    @Override
    public boolean update(IScorePolicy objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "UPDATE score_policies SET winValue =?,loseValue=?,drawValue=? WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(4,objectToUpdate.getId().toString());
        preparedStatement.setInt(1,objectToUpdate.winVal());
        preparedStatement.setInt(2,objectToUpdate.looseVal());
        preparedStatement.setInt(3,objectToUpdate.drowVal());
        int ans = preparedStatement.executeUpdate();

        return ans==1;
    }

    @Override
    public IScorePolicy select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement="SELECT * FROM score_policies WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return new DefaultIScorePolicy(UUID.fromString(objectIdentifier),rs.getInt("winValue"),rs.getInt("loseValue"),rs.getInt("drawValue"));
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
