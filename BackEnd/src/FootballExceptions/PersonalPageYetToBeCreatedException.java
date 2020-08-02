package FootballExceptions;

public class PersonalPageYetToBeCreatedException extends Exception {

    private String message;

    public PersonalPageYetToBeCreatedException() {
        message = "Personal Page Wasn't Created Yet";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
