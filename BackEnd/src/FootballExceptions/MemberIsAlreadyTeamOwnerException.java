package FootballExceptions;

public class MemberIsAlreadyTeamOwnerException extends Exception {
    private String message;

    public MemberIsAlreadyTeamOwnerException() {

        message = "This Member Is Already A Team Owner";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
