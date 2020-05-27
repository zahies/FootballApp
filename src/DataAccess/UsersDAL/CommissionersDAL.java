package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UserInformationDAL.FinancialActivitiesDAL;
import Domain.Users.Commissioner;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class CommissionersDAL implements DAL<Commissioner,String> {

    Connection connection =null;

    @Override
    public boolean insert(Commissioner objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToInsert);
        connection = connect();

        String statement ="INSERT INTO commissioners (userName) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getName());
        preparedStatement.execute();

        LinkedList<Pair<String, Integer>> list = objectToInsert.getFinanceAssociationActivity();
        for (Pair<String,Integer> pair: list) {
            new FinancialActivitiesDAL().insert(new Pair<>(new Pair<>(objectToInsert.getName(),pair.getKey()),pair.getValue()));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Commissioner objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToUpdate);
        connection = connect();

        LinkedList<Pair<String, Integer>> list = objectToUpdate.getFinanceAssociationActivity();
        for (Pair<String,Integer> pair: list) {
            new FinancialActivitiesDAL().update(new Pair<>(new Pair<>(objectToUpdate.getName(),pair.getKey()),pair.getValue()));
        }
        connection.close();
        return true;
    }

    @Override
    public Commissioner select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
