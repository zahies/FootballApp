package Domain.Events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Event_LoggerTest {
    private Event_Logger eventLog ;
    private Goal goal;

    @Before
    public void init(){
        goal=new Goal(30);
        eventLog = new Event_Logger();


    }

    @Test
    public void addEvent() {

        eventLog.addEvent(goal);
        assertEquals((int)goal.getGameMinute(),(int)eventLog.getEvents().get(0).getGameMinute());

    }

    @Test
    public void getEvents() {
    }
}