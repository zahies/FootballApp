package Domain.Alerts;

import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;

import java.util.UUID;

public class TeamManagementAlert implements IAlert {


    private UUID objectID;
    TeamStatus teamStatus;
    String message;
    private boolean hadSent;

    public TeamManagementAlert(String message) {
        objectID = UUID.randomUUID();
        this.message = message;
        hadSent = false;
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
        return "team status = " + teamStatus + " , message = " + message ;
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


    public boolean isHadSent() {
        return hadSent;
    }

    public void setHadSent(boolean hadSent) {
        this.hadSent = hadSent;
    }

}
