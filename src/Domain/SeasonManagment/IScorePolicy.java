package Domain.SeasonManagment;

import java.util.UUID;

public interface IScorePolicy {


    //todo what values to return?
    public int winVal();
    public UUID getId();
    public int looseVal();

    public int drowVal();


}
