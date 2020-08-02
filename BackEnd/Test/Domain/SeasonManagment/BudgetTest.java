package Domain.SeasonManagment;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class BudgetTest {

    private Budget budget;

    @Before
    public void init(){
        budget = new Budget(123);
    }


    @Test
    public void addFinanceActivity() {
        budget.addFinanceActivity(BudgetActivity.BuyPlayer,100);
        budget.addFinanceActivity(BudgetActivity.Tax,200);
        budget.addFinanceActivity(BudgetActivity.BuyPlayer,300);
        LinkedList<Pair<BudgetActivity,Integer>> ans2 = budget.getFinanceActivity();
        assertEquals(3, ans2.size());
    }


    @Test
    public void calculateFinalBudget() {
        int a=100;
        int b=-200;
        int c=300;
        budget.addFinanceActivity(BudgetActivity.BuyPlayer,a);
        budget.addFinanceActivity(BudgetActivity.Tax,b);
        budget.addFinanceActivity(BudgetActivity.BuyPlayer,c);
        int sum = a+b+c;
        int result = budget.calculateFinalBudget();
        assertEquals(sum,result);

    }


    @Test
    public void calculateFinalBudget1() {
        int a=20;
        int b=200;
        int c=30;
        budget.addFinanceActivity(BudgetActivity.BuyPlayer,a);
        budget.addFinanceActivity(BudgetActivity.Tax,b);
        budget.addFinanceActivity(BudgetActivity.BuyPlayer,c);
        int sum = a+b+c-1;
        int result = budget.calculateFinalBudget();
        assertNotEquals(sum,result);
    }



    @Test
    public void setId() {
        budget.setId(333);
        assertEquals(333,budget.getId());
    }

    @Test
    public void findQuarter() {
        boolean flag=true;
        int counter;
        for (int i = 1; i <12 ; i++) {
            counter = ((i-1) / 3) + 1;
            Date date = new Date(1995, i, 01);
            int month = budget.findQuarter(date);
            if (month != counter){
                flag= false;
                break;
            }
        }
        assertTrue(flag);
    }



}