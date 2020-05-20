package FootballExceptions;

public class UnableToRemoveException extends Exception {
    public UnableToRemoveException() {
    }

    public UnableToRemoveException(String message) {
        super(message);
    }
}
