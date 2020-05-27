package Domain.SeasonManagment;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ControlBudgetTest {

    ControlBudget controlBudget;
    Budget budget;
    Date date;
    Date date1;
    Date date2;
    Date date3;


    @Before
    public void init(){
        //controlBudget = new ControlBudget(1234);
       // budget = new Budget(1234);
        date = new Date(1995, 01, 01);
        date1 = new Date(1995, 01, 01);
        date2 = new Date(1995, 04, 01);
        date3 = new Date(1995, 02, 0);

    }


    @Test
    public void addFinanceActivity() {
        int amount;
        int a=100;
        int b=100;
        int c=100;
        controlBudget.addFinanceActivity(date, BudgetActivity.GameIncome, a);
        controlBudget.addFinanceActivity(date1, BudgetActivity.GameIncome, b);
        controlBudget.addFinanceActivity(date2, BudgetActivity.GameIncome, c);
        int quarter1 = controlBudget.budget_quarter_1.calculateFinalBudget();
        amount = a+b;
        assertEquals(amount,quarter1);
    }



    @Test
    public void addFinanceActivity2() {
        int amount;
        int a=100;
        int b=100;
        int c=100;
        controlBudget.addFinanceActivity(date, BudgetActivity.GameIncome, a);
        controlBudget.addFinanceActivity(date1, BudgetActivity.GameIncome, b);
        controlBudget.addFinanceActivity(date2, BudgetActivity.GameIncome, c);
        int quarter2 = controlBudget.budget_quarter_2.calculateFinalBudget();
        amount = c;
        assertEquals(amount,quarter2);
    }



    //todo add assert
    @Test
    public void checkIncomeBiggerThanOutcome() {
        int a=100;
        int b=200;
        int c=-400;
        Date curr_date = new Date();
        controlBudget.addFinanceActivity(curr_date, BudgetActivity.GameIncome, a);
        controlBudget.addFinanceActivity(curr_date, BudgetActivity.GameIncome, b);
        controlBudget.addFinanceActivity(curr_date, BudgetActivity.GameIncome, c);
        boolean result = controlBudget.checkIncomeBiggerThanOutcome();
        assertFalse(result);
    }


    //todo add assert
    @Test
    public void checkIncomeBiggerThanOutcome1() {
        int a=-100;
        int b=200;
        int c=400;
        Date curr_date = new Date();
        controlBudget.addFinanceActivity(curr_date, BudgetActivity.GameIncome, a);
        controlBudget.addFinanceActivity(curr_date, BudgetActivity.GameIncome, b);
        controlBudget.addFinanceActivity(curr_date, BudgetActivity.GameIncome, c);
        boolean result = controlBudget.checkIncomeBiggerThanOutcome();
        assertTrue(result);
    }


    @Test
    public void enforceCommissionerRule() {
        controlBudget.enforceCommissionerRule();
        int count = controlBudget.commissionerRule.payRule();
        assertEquals(count,controlBudget.budget_quarter_2.calculateFinalBudget());
    }





    @Test
    public void findQuarter() {
        boolean flag=true;
        int counter;
        for (int i = 1; i <12 ; i++) {
            counter = ((i-1) / 3) + 1;
            Date date = new Date(1995, i, 01);
            Budget month = controlBudget.findQuarter(date);
            if (month.findQuarter(date) != counter){
                flag= false;
                break;
            }
        }
        assertTrue(flag);
    }



}