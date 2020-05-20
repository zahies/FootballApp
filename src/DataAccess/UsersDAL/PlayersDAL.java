package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.Users.Member;
import Domain.Users.PersonalInfo;
import Domain.Users.Player;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;

public class PlayersDAL implements DAL<Member, String> {

    Connection connection = null;
    MembersDAL membersDAL = new MembersDAL();
    AssetsDAL assetsDAL = new AssetsDAL();
    TeamsDAL teamsDAL = new TeamsDAL();

    @Override
    public boolean insert(Member member) throws SQLException, NoConnectionException, UserInformationException, mightBeSQLInjectionException {
        Member check = null;
        try {
            check = select(member.getName());
        } catch (UserInformationException | UserIsNotThisKindOfMemberException e) {

        }
        if (check == null) {
            membersDAL.insert(member);
            member = ((Player) member);
            assetsDAL.insert((IAsset) member);
            // assetsDAL.insert(FootballManagmentSystem.getInstance().getAllAssests().get(((Player) member).getAssetID()));
            connection = this.connect();
            if (connection == null) {
                throw new NoConnectionException();
            }

            String statement = "INSERT INTO players (UserName,DateOfBirth,Team,PersonalPage,Role, AssetID,FootballRate) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, member.getName());

            java.util.Date date = ((Player) member).getDateOfBirth();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setDate(2, sqlDate);

            if (((Player) member).getMyTeam() == null) {
                preparedStatement.setInt(3, 0);
            } else {
                preparedStatement.setInt(3, ((Player) member).getMyTeam().getId());
            }
            if (((Player) member).getInfo() != null) {
                preparedStatement.setInt(4, ((Player) member).getInfo().getPageID());
            } else {
                preparedStatement.setInt(4, 0);
            }
            preparedStatement.setString(5, ((Player) member).getRole());
            preparedStatement.setInt(6, ((Player) member).getAssetID());
            preparedStatement.setDouble(7, ((Player) member).getFootballRate());
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
            throw new NoConnectionException();
        }
        if (valToUpdate.getKey().equals("AssetVal")) {
            return assetsDAL.update(((IAsset) member), valToUpdate);
        }
        String statement = "UPDATE players SET " + valToUpdate.getKey() + " =  ? " +
                "WHERE UserName = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        //preparedStatement.setString(1,valToUpdate.getKey());
        preparedStatement.setString(2, member.getName());

        if (valToUpdate.getValue() instanceof Boolean) {
            preparedStatement.setBoolean(1, ((Boolean) valToUpdate.getValue()));
        } else if (valToUpdate.getValue() instanceof String) {
            preparedStatement.setString(1, ((String) valToUpdate.getValue()));
        } else if (valToUpdate.getValue() instanceof Double) {
            preparedStatement.setDouble(1, ((Double) valToUpdate.getValue()));
        } else if (valToUpdate.getValue() instanceof Date) {
            preparedStatement.setDate(1, new java.sql.Date(((Date) valToUpdate.getValue()).getTime()));
        } else {
            preparedStatement.setInt(1, ((Integer) valToUpdate.getValue()));
        }
        preparedStatement.executeUpdate();
        connection.close();
        return true;
    }

    @Override
    public Member select(String userName) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {

        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        /**MEMBER DETAILS*/
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

        /**PLAYER DETAILS*/
        statement = "SELECT Team,PersonalPage,Role, AssetID,FootballRate,DateOfBirth FROM players WHERE UserName = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, userName);
        rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserIsNotThisKindOfMemberException();
        }

        int teamID = rs.getInt(1);
        Team team = teamsDAL.select(teamID);
        int personlPID = rs.getInt(2);
        PersonalInfo page = new PersonalPagesDAL().select(personlPID);
        String role = rs.getString(3);
        int assetID = rs.getInt(4);
        double rate = rs.getDouble(5);
        java.util.Date dateOfBirth = rs.getDate(6);

        /**ASSET DETAILS*/
        statement = "SELECT AssetVal FROM assets WHERE AssetID = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, assetID);
        rs = preparedStatement.executeQuery();
        rs.next();
        int assetVal = rs.getInt(1);

        Member member = new Player(userName, password, realName, assetVal, assetID, team, role, page, dateOfBirth, rate);
        connection.close();
        return member;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }
}
