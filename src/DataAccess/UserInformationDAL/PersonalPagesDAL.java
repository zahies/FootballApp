package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.PersonalInfo;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PersonalPagesDAL implements DAL<PersonalInfo, Integer> {
    Connection connection = null;
    @Override
    public boolean insert(PersonalInfo objectToInsert) throws SQLException, NoConnectionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException, mightBeSQLInjectionException, UserInformationException, NoPermissionException {

        connection = connect();

        String statement = "INSERT INTO personal_pages (PageID, title, Profile) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getPageID());
        preparedStatement.setString(2, objectToInsert.getPageTitle());
        preparedStatement.setString(3, objectToInsert.getProfile().getObjectID().toString());
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
    public PersonalInfo select(Integer objectIdentifier) throws SQLException, UserInformationException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
