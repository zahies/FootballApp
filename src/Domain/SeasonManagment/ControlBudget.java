package Domain.SeasonManagment;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.ControlBudgetDAL;
import Domain.Alerts.FinancialAlert;
import Domain.FootballManagmentSystem;
import Domain.Users.Commissioner;
import Domain.Users.Member;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class ControlBudget {


    UUID objectID;
    Budget budget_quarter_1;
    Budget budget_quarter_2;
    Budget budget_quarter_3;
    Budget budget_quarter_4;
    ICommissionerRule commissionerRule;

    public ControlBudget(UUID teamID) {
        budget_quarter_1 = new Budget(teamID);
        budget_quarter_2 = new Budget(teamID);
        budget_quarter_3 = new Budget(teamID);
        budget_quarter_4 = new Budget(teamID);
        budget_quarter_1.setControlBudgetID(objectID);
        budget_quarter_2.setControlBudgetID(objectID);
        budget_quarter_3.setControlBudgetID(objectID);
        budget_quarter_4.setControlBudgetID(objectID);
        commissionerRule = new DefaultCommissionerRule();
        objectID = UUID.randomUUID();
    }


    public void addFinanceActivity(Date date, BudgetActivity description, int amount) {
        Budget budget = findQuarter(date);
        budget.addFinanceActivity(description, amount);
    }


    //todo pop up alters

    /**
     * constraint 7  (run by the end of the quarter)
     */
    public boolean checkIncomeBiggerThanOutcome() {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        Date date = new Date();
        Budget budget = findQuarter(date);
        if (budget.calculateFinalBudget() < 0) {

            HashMap<String, LinkedList<Member>> members = system.getMembers();
            for (String name : members.keySet()) {
                for (int i = 0; i < members.get(name).size(); i++) {
                    if (members.get(name).get(i) instanceof Commissioner) {
                        members.get(name).get(i).handleAlert(new FinancialAlert(budget.calculateFinalBudget()));
                    }
                }
            }

            System.out.println(" WARNING THE OUTCOMES ARE BIGGER THAN INCOMES !!!");
            return false;
        } else {
            System.out.println(" YOUR BUDGET IS BALANCED :) ");
            return true;
        }
    }


    public int getCurrentBudget(Date date) {
        Budget budget = findQuarter(date);
        return budget.calculateFinalBudget();
    }


    public Budget findQuarter(Date date) {
        int month = date.getMonth();

        //January, February, March
        if ((month >= 0) && (month <= 2)) {
            return budget_quarter_1;
        }
        //April, May, June
        else if ((month >= 3) && (month <= 5)) {
            return budget_quarter_2;
        }
        //July, August, September
        else if ((month >= 6) && (month <= 8)) {
            return budget_quarter_3;
        }
        //October, November, December
        else
            return budget_quarter_4;
    }


    public void setCommissionerRule(ICommissionerRule commissionerRule) {
        this.commissionerRule = commissionerRule;
    }


    public void enforceCommissionerRule() {
        Date date = new Date();
        Budget budget = findQuarter(date);
        budget.addFinanceActivity(BudgetActivity.Tax, commissionerRule.payRule());
    }


    public ICommissionerRule getICommissionerRule() {
        return commissionerRule;
    }

    public UUID getObjectID() {
        return objectID;
    }

    public ICommissionerRule getCommissionerRule() {
        return commissionerRule;
    }
}
