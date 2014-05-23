package exception;

/**
 * Created by kate on 23.04.14.
 */
public class RepeatedLoginException extends Exception {
    public RepeatedLoginException(String login) {
        super("The user with username " + login + " is already exists.");
    }
}
