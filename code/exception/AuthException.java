package exception;

public class AuthException extends Exception {

    public AuthException(){
        super("Authentication Error has occured!");
    }

    public AuthException(String message) {
        super("Authentication Error\n" + message);
    }
  
}
