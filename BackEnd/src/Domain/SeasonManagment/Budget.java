package Domain.SeasonManagment;

import javafx.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Budget {


    private LinkedList<Pair<BudgetActivity, Integer>> financeActivity;
    /**
     * description_cost    cost = Income/Outcome  description = Income/Outcome explained
     */
    private int teamID;


    /**
     * constructor
     *
     * @param teamID
     */
    public Budget(int teamID) {
        this.teamID = teamID;
        financeActivity = new LinkedList<>();
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
     * getter
     *
     * @return
     */
    public int getId() {
        return teamID;
    }

    /**
     * setter
     *
     * @param teamID
     */
    public void setId(int teamID) {
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


}
