package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.BudgetsDAL;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class Budget {


    private LinkedList<Pair<BudgetActivity, Integer>> financeActivity;
    /**
     * description_cost    cost = Income/Outcome  description = Income/Outcome explained
     */
    private UUID teamID;
    private UUID objectID;
    private UUID controlBudgetID;
    /**
     * constructor
     *
     * @param teamID
     */
    public Budget(UUID teamID) {
        this.teamID = teamID;
        objectID = UUID.randomUUID();
        financeActivity = new LinkedList<>();
    }

    public Budget(LinkedList<Pair<BudgetActivity, Integer>> financeActivity, UUID teamID, UUID objectID, UUID controlBudgetID) {
        this.financeActivity = financeActivity;
        this.teamID = teamID;
        this.objectID = objectID;
        this.controlBudgetID = controlBudgetID;
    }

    public void addFinanceActivity(BudgetActivity desc, int amount) {
        Pair<BudgetActivity, Integer> pair = new Pair<>(desc, amount);
        financeActivity.add(pair);
    }


    /**
     * calculates the final budget
     */
    public int calculateFinalBudget() {
        int sum = 0;
        for (int i = 0; i < financeActivity.size(); i++) {
            sum += financeActivity.get(i).getValue();
        }
        return sum;
    }




    /**
     * setter
     *
     * @param teamID
     */
    public void setId(UUID teamID) {
        this.teamID = teamID;
    }


    /**
     * gets a date, extracts the month and returns the number of the quarter it should be
     *
     * @param date
     * @return number of quarter
     */
    public int findQuarter(Date date) {
        int month = date.getMonth();

        //January, February, March
        if ((month >= 1) && (month <= 3)) {
            return 1;
        }
        //April, May, June
        else if ((month >= 4) && (month <= 6)) {
            return 2;
        }
        //July, August, September
        else if ((month >= 7) && (month <= 9)) {
            return 3;
        }
        //October, November, December
        else
            return 4;
    }

    public LinkedList<Pair<BudgetActivity, Integer>> getFinanceActivity() {
        return financeActivity;
    }

    public UUID getTeamID() {
        return teamID;
    }

    public UUID getObjectID() {
        return objectID;
    }

    public UUID getControlBudgetID() {
        return controlBudgetID;
    }

    public void setControlBudgetID(UUID controlBudgetID) {
        this.controlBudgetID = controlBudgetID;
    }
}
