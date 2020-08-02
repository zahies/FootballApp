package Domain.Alerts;

import java.util.Observable;

public interface IGameSubjective {

    public void addTeamsFans();

    public void addReferees();

    public void notifyReferees(IAlert newAlert);

    public void notifyTeamfans(IAlert newAlert);
}
