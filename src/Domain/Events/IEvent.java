package Domain.Events;

import Domain.Users.Player;

import java.util.Date;

public interface IEvent {

    public Player getPlayerWhocommit();

    public double getGameMinute();






}
