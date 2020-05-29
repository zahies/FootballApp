package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import Domain.SeasonManagment.Team;
import Domain.Users.Member;
import Domain.Users.TeamOwner;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;

public class TeamOwnersDAL implements DAL<TeamOwner, String> {

    Connection connection = null;


    @Override
    public boolean insert(TeamOwner member) throws SQLException, UserInformationException, NoConnectionException, mightBeSQLInjectionException, NoPermissionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException {

        if (!checkExist(member.getName(), "teamowners", "UserName","")) {
            new MembersDAL().insert(member);
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
    public boolean update(TeamOwner member) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        new MembersDAL().update(member);
        connection = connect();

        String statement = "UPDATE teamowners SET Team =  ? WHERE UserName = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, ((TeamOwner)member).getTeam().getId().toString());
        preparedStatement.setString(2, member.getName());

        int ans = preparedStatement.executeUpdate();
        connection.close();
        return ans ==1;
    }


    public TeamOwner select(String userName, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {

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

        String teamID = rs.getString(1);
        Team team = null;
        if(bidirectionalAssociation) {
            team = new TeamsDAL().select(teamID,true);
        }

        TeamOwner member = new TeamOwner(userName, password, realName, team);
        connection.close();
        return member;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }
}
