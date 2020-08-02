package FootballExceptions;

public class InvalidTeamAssetException extends Exception {

    private String message;

    public InvalidTeamAssetException() {
        message = "Invalid Team Asset";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
