package Domain.Alerts;

import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;

import java.util.UUID;

public class TeamManagementAlert implements IAlert {


    private UUID objectID;
    TeamStatus teamStatus;
    String message;
/***FOR DB*/
    public TeamManagementAlert(UUID objectID, TeamStatus teamStatus, String message) {
        this.objectID = objectID;
        this.teamStatus = teamStatus;
        this.message = message;
    }

    public TeamManagementAlert(String message, TeamStatus teamStatus) {
        objectID = UUID.randomUUID();
        this.message = message;
        this.teamStatus = teamStatus;
    }

    public TeamManagementAlert(TeamStatus teamStatus, Team team) {
        this.teamStatus = teamStatus;
        objectID = UUID.randomUUID();
        message = "Team: " + team.getName() + " status changed to:" + teamStatus.toString();
    }

    public UUID getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return "team status = " + teamStatus + " , message = " + message +
                " }";
    }


    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "Team Management Alert";
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public String getMessage() {
        return message;
    }

}
