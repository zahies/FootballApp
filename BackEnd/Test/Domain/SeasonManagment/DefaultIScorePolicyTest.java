package Domain.SeasonManagment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultIScorePolicyTest {

    DefaultIScorePolicy defaultIScorePolicy;

    @Before
    public void init(){
        defaultIScorePolicy = new DefaultIScorePolicy();
    }


    @Test
    public void winVal() {
        assertEquals(3, defaultIScorePolicy.winVal());
    }

    @Test
    public void looseVal() {
        assertEquals(0, defaultIScorePolicy.looseVal());

    }

    @Test
    public void drowVal() {
        assertEquals(1, defaultIScorePolicy.drowVal());

    }
}