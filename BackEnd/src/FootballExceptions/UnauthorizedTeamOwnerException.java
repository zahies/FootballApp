package FootballExceptions;

public class UnauthorizedTeamOwnerException extends Exception {
    private String message;

    public UnauthorizedTeamOwnerException() {
        message = "This Team Owner Isn't Authorized To Do This Action";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
