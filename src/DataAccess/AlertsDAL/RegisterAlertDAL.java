package DataAccess.AlertsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.SeasonManagmentDAL.LeaguesDAL;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.Alerts.PersonalPageAlert;
import Domain.Alerts.RegistrationRequestAlert;
import Domain.Users.Coach;
import Domain.Users.TeamOwner;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RegisterAlertDAL implements DAL<RegistrationRequestAlert,String> {



    @Override
    public boolean insert(RegistrationRequestAlert objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "INSERT INTO member_alerts_register (ObjectID, League, Team, Year,Owner) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setString(2,objectToInsert.getLeaugue().getObjectID().toString());
        preparedStatement.setString(3,objectToInsert.getTeamName());
        preparedStatement.setInt(4,objectToInsert.getYear());
        preparedStatement.setString(5,objectToInsert.getOwner().getName());

        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(RegistrationRequestAlert objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        return false;
    }

    @Override
    public RegistrationRequestAlert select(String objectIdentifier, boolean bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, EmptyPersonalPageException {
        Connection connection = MySQLConnector.getInstance().connect();


        String statement = "SELECT * FROM member_alerts_register WHERE ObjectID=?";
        PreparedStatement preparedStatement =connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        statement = "SELECT * FROM member_alerts WHERE AlertObjectID=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs2 = preparedStatement.executeQuery();
        rs.next();
        rs2.next();

        return new RegistrationRequestAlert(new LeaguesDAL().select(rs.getString("League"),false),rs.getString("Team"),rs.getInt("Year"),UUID.fromString(objectIdentifier),new TeamOwnersDAL().select(rs.getString("Owner"),false),rs2.getBoolean("Sent"));
    }

    @Override
    public boolean delete(String objectIdentifier) throws NoConnectionException, SQLException {
        return false;
    }
}
