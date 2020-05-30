package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.Alerts.IAlert;
import Domain.Users.*;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MembersDAL implements DAL<Member, String> {


    @Override
    public boolean insert(Member member) throws SQLException, NoConnectionException, UserInformationException, mightBeSQLInjectionException, NoPermissionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException {

        Connection connection = MySQLConnector.getInstance().connect();
        if (checkExist(member.getName(), "members", "UserName",member.getClass().toString())) {
            throw new UserInformationException();
        }
        String statement = "INSERT INTO members (UserName,Password, RealName, MailAddress,isActive,AlertsViaMail,Type) VALUES (?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, member.getName());
        preparedStatement.setString(2, member.getPassword());
        preparedStatement.setString(3, member.getReal_Name());
        preparedStatement.setString(4, member.getMailAddress());
        preparedStatement.setBoolean(5, member.isActive());
        preparedStatement.setBoolean(6, member.isAlertViaMail());
        if(member instanceof Coach){
            preparedStatement.setString(7,"Coach");
        }else if(member instanceof Commissioner){
            preparedStatement.setString(7,"Commissioner");
        }else if(member instanceof Fan){
            preparedStatement.setString(7,"Fan");
        }else if(member instanceof Player){
            preparedStatement.setString(7,"Player");
        }else if(member instanceof Referee){
            preparedStatement.setString(7,"Referee");
        }else if(member instanceof SystemManager){
            preparedStatement.setString(7,"SystemManager");
        }else if(member instanceof TeamManager){
            preparedStatement.setString(7,"TeamManager");
        }else if(member instanceof TeamOwner){
            preparedStatement.setString(7,"TeamOwner");
        }
        preparedStatement.execute();

        Queue<IAlert> memberAlerts = member.getAlertsList();
        for (IAlert alert : memberAlerts) {
            new MemberAlertsDAL().insert(new Pair<>(new Pair<>(member.getName(),alert),alert.getType()));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Member member) throws SQLException, NoConnectionException, mightBeSQLInjectionException, UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, DuplicatedPrimaryKeyException {

        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "UPDATE members SET Password=?, RealName=?, MailAddress=?,isActive=?,AlertsViaMail =  ? WHERE UserName = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(6, member.getName());
        preparedStatement.setString(1, member.getPassword());
        preparedStatement.setString(2, member.getReal_Name());
        preparedStatement.setString(3, member.getMailAddress());
        preparedStatement.setBoolean(4, member.isActive());
        preparedStatement.setBoolean(5, member.isAlertViaMail());
        int ans = preparedStatement.executeUpdate();

        Queue<IAlert> memberAlerts = member.getAlertsList();
        for (IAlert alert : memberAlerts) {
            new MemberAlertsDAL().update(new Pair<>(new Pair<>(member.getName(),alert),alert.getType()));
        }

        connection.close();
        return ans ==1;
    }

    @Override
    public Member select(String userName, boolean  bidirectionalAssociation) throws NoConnectionException, SQLException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException {

        Connection connection = MySQLConnector.getInstance().connect();
        String statement ="SELECT UserName , Type FROM members";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        String type = rs.getString("Type");
        String userNameFromTable = rs.getString("UserName");
        Member member = null;
        switch (type) {
            case "Coach":
                member = new CoachesDAL().select(userName, true);
                break;
            case "Commissioner":
                member = new CommissionersDAL().select(userName, true);
                break;
            case "Fan":
                member = new FansDAL().select(userName, true);
                break;
            case "Player":
                member = new PlayersDAL().select(userName, true);
                break;
            case "Referee":
                member = new RefereesDAL().select(userName, true);
                break;
            case "SystemManager":
                member = new SystemManagerDAL().select(userName, true);
                break;
            case "TeamManager":
                member = new TeamManagerDAL().select(userName, true);
                break;
            case "TeamOwner":
                member = new TeamOwnersDAL().select(userName, true);
                break;
        }

        return member;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }

    @Override
    public boolean checkExist(String objectIdentifier, String tableName, String primaryKeyName, String primaryKeyName2) throws NoConnectionException, SQLException, mightBeSQLInjectionException {
        Connection connection = MySQLConnector.getInstance().connect();
//        if (!allTablesName.contains(tableName) || !allPrimaryKeysName.contains(primaryKeyName)|| !allPrimaryKeysName.contains(primaryKeyName2)) {
//            throw new mightBeSQLInjectionException();
//        }
        String statement = "SELECT * FROM members Where UserName = ? and  Type= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        preparedStatement.setString(2,primaryKeyName2);
        ResultSet rs = preparedStatement.executeQuery();
        boolean ans = rs.next();
        connection.close();
        return ans;
    }

    public HashMap<String, LinkedList<Member>> selectAll() throws NoConnectionException, SQLException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException {
        HashMap<String, LinkedList<Member>> allMembers = new HashMap<>();

        Connection connection = MySQLConnector.getInstance().connect();
        String statement ="SELECT UserName , Type FROM members";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            String type = rs.getString("Type");
            String userName = rs.getString("UserName");
            Member member = null;
            switch (type) {
                case "Coach":
                    member = new CoachesDAL().select(userName, true);
                    break;
                case "Commissioner":
                    member = new CommissionersDAL().select(userName, true);
                    break;
                case "Fan":
                    member = new FansDAL().select(userName, true);
                    break;
                case "Player":
                    member = new PlayersDAL().select(userName, true);
                    break;
                case "Referee":
                    member = new RefereesDAL().select(userName, true);
                    break;
                case "SystemManager":
                    member = new SystemManagerDAL().select(userName, true);
                    break;
                case "TeamManager":
                    member = new TeamManagerDAL().select(userName, true);
                    break;
                case "TeamOwner":
                    member = new TeamOwnersDAL().select(userName, true);
                    break;
                }
            if(!allMembers.containsKey(userName)){
                LinkedList <Member> memberAccounts = new LinkedList<>();
                memberAccounts.add(member);
                allMembers.put(userName,memberAccounts);
            }else {
                LinkedList <Member> memberAccounts =allMembers.get(userName);
                memberAccounts.add(member);
                allMembers.replace(userName,memberAccounts);
            }
        }
        return allMembers;
    }
}
