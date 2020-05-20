package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import Domain.SeasonManagment.Team;
import Domain.Users.Member;
import Domain.Users.TeamOwner;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;

public class TeamOwnersDAL implements DAL<Member, String> {

    Connection connection = null;
    MembersDAL membersDAL = new MembersDAL();
    TeamsDAL teamsDAL = new TeamsDAL();

    @Override
    public boolean insert(Member member) throws SQLException, UserInformationException, NoConnectionException, mightBeSQLInjectionException {

        if (!checkExist(member.getName(), "teamowners", "UserName")) {
            membersDAL.insert(member);
            member = ((TeamOwner) member);
            connection = this.connect();
            if (connection == null) {
                throw new NoConnectionException();
            }
            String statement = "INSERT INTO teamowners (UserName) VALUES (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, member.getName());
            preparedStatement.execute();
            connection.close();
            return true;
        } else {
            throw new UserInformationException();
        }
    }

    @Override
    public boolean update(Member member, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException {

        this.select(member.getName());
        if (valToUpdate.getKey().equals("UserName") || valToUpdate.getKey().equals("RealName") || valToUpdate.getKey().equals("Password") || valToUpdate.getKey().equals("isActive") || valToUpdate.getKey().equals("AlertsViaMail") || valToUpdate.getKey().equals("MailAddress")) {
            return membersDAL.update(member, valToUpdate);
        }
        connection = connect();
        if (connection == null) {
            return false;
        }
        String statement = "UPDATE teamowners SET " + valToUpdate.getKey() + " =  ? " +
                "WHERE UserName = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        //preparedStatement.setString(1,valToUpdate.getKey());
        preparedStatement.setInt(1, ((Integer) valToUpdate.getValue()));
        preparedStatement.setString(2, member.getName());

        preparedStatement.executeUpdate();
        connection.close();
        return true;
    }

    @Override
    public Member select(String userName) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException {

        /**MEMBER DETAILS*/
        connection = connect();
        String statement = "SELECT Password,RealName,MailAddress,isActive, AlertsViaMail FROM members WHERE UserName = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, userName);
        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserInformationException();
        }
        String password = rs.getString(1);
        String realName = rs.getString(2);
        String mail = rs.getString(3);
        boolean isActive = rs.getBoolean(4);
        boolean AlertsViaMail = rs.getBoolean(5);

        /***TEAM OWNER DETAILS*/
        statement = "SELECT Team FROM teamowners WHERE UserName = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, userName);
        rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserIsNotThisKindOfMemberException();
        }

        int teamID = rs.getInt(1);
        Team team = teamsDAL.select(teamID);

        Member member = new TeamOwner(userName, password, realName, team);
        connection.close();
        return member;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }
}
