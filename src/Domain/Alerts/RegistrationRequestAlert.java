package Domain.Alerts;

import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Team;
import Domain.Users.TeamOwner;

import java.util.UUID;

public class RegistrationRequestAlert implements IAlert{
    private Leaugue leaugue;
    private String teamName;
    private int year;
    private UUID objectID;
    private TeamOwner owner;

    public RegistrationRequestAlert(String teamName, Leaugue leaugue, int year, TeamOwner owner) {
        this.leaugue = leaugue;
        this.year = year;
        this.objectID = UUID.randomUUID();
        this.owner = owner;
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "team name = " + teamName + " , is willing to register to league = " + leaugue.getID() +" , inside season at year = " + year +
                " }";
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return getClass().getName();
    }

    @Override
    public UUID getObjectID() {
        return objectID;
    }

    public String getTeamName() {
        return teamName;
    }

    public Leaugue getLeaugue() {
        return leaugue;
    }

    public int getYear() {
        return year;
    }

    public TeamOwner getOwner(){
        return owner;
    }
}
