package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UsersDAL.FansDAL;
import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.Fan;
import Domain.Users.PersonalInfo;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PersonalPagesDAL implements DAL<PersonalInfo, Integer> {

    @Override
    public boolean insert(PersonalInfo objectToInsert) throws SQLException, NoConnectionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException, mightBeSQLInjectionException, UserInformationException, NoPermissionException {

        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "INSERT INTO personal_pages (PageID, title) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getPageID());
        preparedStatement.setString(2, objectToInsert.getPageTitle());
        preparedStatement.execute();

        List <APersonalPageContent> contents = objectToInsert.getPageContent();
        for (APersonalPageContent content : contents) {
            new PersonalPageContentDAL().insert(content);
        }
        connection.close();

        return true;
    }

    @Override
    public boolean update(PersonalInfo objectToUpdate) throws SQLException {
        return false;
    }

    @Override
    public PersonalInfo select(Integer objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, NoConnectionException, EmptyPersonalPageException, NoPermissionException, UserIsNotThisKindOfMemberException {
        Connection connection = MySQLConnector.getInstance().connect();

        /**PersonalPage Details*/
        String statement = "SELECT * FROM personal_pages WHERE PageID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        String title = rs.getString("title");
        LinkedList<Fan> followers = new LinkedList<>();

        /***FOLLOWERS*/
        statement = "SELECT * FROM fan_following_pages WHERE PersonalPageID=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            Fan follower = new FansDAL().select(rs.getString("MemberUserName"),false);
        }

        return  new PersonalInfo(objectIdentifier,null,null,null,null,null,followers);
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }

    public HashMap<Integer, PersonalInfo> selectAll(){
        HashMap<Integer, PersonalInfo> allPages= new HashMap<>();
        return allPages;
    }
}
