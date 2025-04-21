package exception;

/**
 * The AuthException class represents an exception that occurs during authentication.
 * It extends the Exception class and provides constructors for default and custom error messages.
 */
public class AuthException extends Exception {

    /**
     * Constructs a new AuthException with a default error message.
     */
    public AuthException() {
        super("Authentication Error has occurred!");
    }

    /**
     * Constructs a new AuthException with a custom error message.
     *
     * @param message The custom error message to include in the exception.
     */
    public AuthException(String message) {
        super("Authentication Error\n" + message);
    }
}
