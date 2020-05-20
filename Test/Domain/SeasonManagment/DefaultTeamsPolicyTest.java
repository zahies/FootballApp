package Domain.SeasonManagment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultTeamsPolicyTest {


    DefaultTeamsPolicy defaultTeamsPolicy;


    @Before
    public void init(){
        defaultTeamsPolicy = new DefaultTeamsPolicy();
    }


    @Test
    public void numOfGamesWithEachTeam() {
        assertEquals(2, defaultTeamsPolicy.numOfGamesWithEachTeam());
    }
}

