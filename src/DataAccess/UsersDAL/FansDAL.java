package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UserInformationDAL.FanFollowingPagesDAL;
import Domain.Users.Fan;
import Domain.Users.PersonalInfo;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class FansDAL implements DAL<Fan, String> {
    Connection connection = null;

    @Override
    public boolean insert(Fan objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToInsert);
        connection = connect();

        if (checkExist(objectToInsert.getName(), "fans", "UserName","")) {
            throw new DuplicatedPrimaryKeyException();
        }

        String statement = "INSERT INTO fans(UserName) VALUES(?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        preparedStatement.execute();

        HashMap<PersonalInfo, Boolean> personalPagesFollowed = objectToInsert.getPersonalPagesFollowed();
        for (PersonalInfo page : personalPagesFollowed.keySet()) {
            new FanFollowingPagesDAL().insert(new Pair<>(new Pair<>(objectToInsert.getName(), page.getPageID()), personalPagesFollowed.get(page)));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Fan objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();
        new MembersDAL().update(objectToUpdate);

        HashMap<PersonalInfo, Boolean> personalPagesFollowed = objectToUpdate.getPersonalPagesFollowed();
        for (PersonalInfo page : personalPagesFollowed.keySet()) {
            new FanFollowingPagesDAL().update(new Pair<>(new Pair<>(objectToUpdate.getName(), page.getPageID()), personalPagesFollowed.get(page)));
        }
        connection.close();
        return true;
    }

    @Override
    public Fan select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
