package exceptions;

public class LoginTakenException extends Exception {
    public LoginTakenException(String message) {
        super(message);
    }
}
