package Domain.Alerts;

import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;

public class TeamManagmentAlert implements IAlert {


    TeamStatus teamStatus;
    String message;

    public TeamManagmentAlert(String message) {
        this.message = message;
    }

    public TeamManagmentAlert(TeamStatus teamStatus, Team team) {
        this.teamStatus = teamStatus;
        message = "Team: " + team.getName() + " status changed to:" + teamStatus.toString();
    }

    public TeamManagmentAlert() {
    }

    @Override
    public String toString() {
        return "TeamManagmentAlert{" +
                "team status = " + teamStatus + " , message = " + message +
                " }";
    }

    @Override
    public String view() {
        return this.toString();
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public String getMessage() {
        return message;
    }

}
