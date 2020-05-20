package FootballExceptions;

public class UnauthorizedPageOwnerException extends Exception {
    private String message;

    public UnauthorizedPageOwnerException() {

        message = "This Page Owner Isn't Authorized To Do This Action";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
