package Domain.Alerts;

import java.util.UUID;

public interface IAlert {

    public String view();
    public String getType();
    public UUID getObjectID();
    public default boolean isSent(){
     return false; //todo - impliment
    }

}
