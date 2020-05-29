package Domain.Events;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Error_Loger {


    private UUID objectID;
    public List<IEvent> errors;



    public Error_Loger() {
        errors = new LinkedList<>();
        objectID = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return objectID;
    }

    public void addError(IEvent event) {
        errors.add(event);
    }

    public List<IEvent> getEvents() {
        return errors;
    }


    @Override
    public String toString() {
        return "Error_Logger{" +
                "errors=" + errors +
//                ", game=" + game +
                '}';
    }
}
