package Domain.SeasonManagment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultCommissionerRuleTest {

    ICommissionerRule iCommissionerRule;


    @Before
    public void init(){
      iCommissionerRule = new DefaultCommissionerRule();
    }


    @Test
    public void payRule() {
        int result = iCommissionerRule.payRule();
        assertEquals(100, result);
    }

    @Test
    public void tostring() {
        iCommissionerRule.toString();
    }


}