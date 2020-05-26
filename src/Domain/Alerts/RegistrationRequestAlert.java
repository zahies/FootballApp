package Domain.Alerts;

import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Team;

public class RegistrationRequestAlert implements IAlert{
    private Team team;
    private Leaugue leaugue;
    private int year;

    public RegistrationRequestAlert(Team team, Leaugue leaugue, int year) {
        this.team = team;
        this.leaugue = leaugue;
        this.year = year;
    }

    @Override
    public String toString() {
        return "team name = " + team.getName() + " , is willing to register to league = " + leaugue.getID() +" , inside season at year = " + year +
                " }";
    }

    @Override
    public String view() {
        return this.toString();
    }

    public Team getTeam() {
        return team;
    }

    public Leaugue getLeaugue() {
        return leaugue;
    }

    public int getYear() {
        return year;
    }
}
