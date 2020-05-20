package FootballExceptions;

public class UnauthorizedTeamManagerException extends Exception {

    private String message;

    public UnauthorizedTeamManagerException() {
        message = "This Team Manager Isn't Authorized To Do This Action";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
