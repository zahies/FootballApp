package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Users.Member;
import Domain.Users.TeamOwner;
import FootballExceptions.UserInformationException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MembersDAL implements DAL<Member, String> {

    Connection connection = null;

    @Override
    public boolean insert(Member member) throws SQLException, NoConnectionException, UserInformationException, mightBeSQLInjectionException {

        connection = this.connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        if (checkExist(member.getName(), "members", "UserName")) {
            throw new UserInformationException();
        }
        String statement = "INSERT INTO members (UserName,Password, RealName, MailAddress,isActive,AlertsViaMail) VALUES (?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, member.getName());
        preparedStatement.setString(2, member.getPassword());
        preparedStatement.setString(3, member.getReal_Name());
        preparedStatement.setString(4, null);
        preparedStatement.setBoolean(5, true);
        preparedStatement.setBoolean(6, true);
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Member member, Pair<String, Object> valToUpdate) throws SQLException, NoConnectionException {

        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }

        String statement = "UPDATE members SET " + valToUpdate.getKey() + " =  ? " +
                "WHERE UserName = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        //preparedStatement.setString(1,valToUpdate.getKey());
        preparedStatement.setString(2, member.getName());

        if (valToUpdate.getValue() instanceof Boolean) {
            preparedStatement.setBoolean(1, ((Boolean) valToUpdate.getValue()));
        } else {
            preparedStatement.setString(1, ((String) valToUpdate.getValue()));
        }
        preparedStatement.executeUpdate();
        connection.close();
        return true;
    }

    @Override
    public Member select(String userName) {
        return null;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }
}
