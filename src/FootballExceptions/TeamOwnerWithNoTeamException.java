package FootballExceptions;

public class TeamOwnerWithNoTeamException extends Exception {

    private String message;

    public TeamOwnerWithNoTeamException() {
        message = "Team Owner Has No Team";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
