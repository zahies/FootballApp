package FootballExceptions;

public class InactiveTeamException extends Exception {

    private String message;

    public InactiveTeamException() {
        message = "Cant Operate Inactive Team!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
