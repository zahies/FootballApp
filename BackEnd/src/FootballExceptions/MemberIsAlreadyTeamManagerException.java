package FootballExceptions;

public class MemberIsAlreadyTeamManagerException extends Exception {
    private String message;

    public MemberIsAlreadyTeamManagerException() {

        message = "This Member Is Already A Team Manager";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
