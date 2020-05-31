package DataAccess;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Users.Member;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import com.mysql.jdbc.MySQLConnection;
import javafx.util.Pair;

import java.sql.*;
import java.util.*;

public interface DAL<T, E> {

    public static final Set<String> allPrimaryKeysName = new HashSet<>(Arrays.asList("UserName", "objectID", "gameID", "AssetID","Info","Commissioner","BudgetActivity","Budget","MemberUserName","PersonalPageID","League","Season","AlertObjectID","PageID","Description","TeamManager","Referee","Game","Season","Referee","SeasonID","TeamID"));
    public static final Set<String> allTablesName = new HashSet<>(Arrays.asList("assets","association_financial_activities","budget_finance_activity","budgets","coaches","commissioner_rules","commissioners","complaint_forms","control_budgets","events","events_logger","events_substitutions","fan_following_pages","fans","fields","games","leagues_seasons","leauge","member_alerts","member_alerts_changed_game","member_alerts_complaints","member_alerts_financial","member_alerts_game_alert","member_alerts_management","member_alerts_personal_page","members","page_content","page_content_career","page_content_profile","permissions","personal_pages","place_teams_policies","players","referee_games","referees","score_policies","season_referees","seasons_teams","seasons","system_managers","teammanagers","teamowners","teams"));

    /**
     * TO PREVENT SQL INJECTION
     */
    public static MySQLConnector mySQLConnector = MySQLConnector.getInstance();
    public boolean insert(T objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException;

    public boolean update(T objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException;

    public T select(E objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, EmptyPersonalPageException;

    public boolean delete(E objectIdentifier) throws NoConnectionException, SQLException;

    public default boolean checkExist(E objectIdentifier, String tableName, String primaryKeyName, String primaryKeyName2) throws NoConnectionException, SQLException, mightBeSQLInjectionException {

        Connection connection = mySQLConnector.connect();
        if (!allTablesName.contains(tableName) || !allPrimaryKeysName.contains(primaryKeyName)|| !allPrimaryKeysName.contains(primaryKeyName2)) {
            throw new mightBeSQLInjectionException();
        }
        String statement = "SELECT * FROM " + tableName + " Where " + primaryKeyName + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (objectIdentifier instanceof String) {
            preparedStatement.setString(1, ((String) objectIdentifier));
        } else if(objectIdentifier instanceof Integer){
            preparedStatement.setInt(1, ((Integer) objectIdentifier));
        } else if(objectIdentifier instanceof Pair){
            statement = "SELECT * FROM " + tableName + " Where " + primaryKeyName + " = ? and " + primaryKeyName2 +" =?";
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,((Pair) objectIdentifier).getKey().toString());
            if(((Pair) objectIdentifier).getValue()instanceof Integer){
                preparedStatement.setInt(2, (Integer) ((Pair) objectIdentifier).getValue());
            }else {
                preparedStatement.setString(2, (String) ((Pair) objectIdentifier).getValue());
            }
        }
        ResultSet rs = preparedStatement.executeQuery();
        boolean ans = rs.next();
        MySQLConnector.getInstance().disconnect();
        return ans;
    }


}
