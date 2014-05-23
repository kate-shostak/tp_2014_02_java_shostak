package exception;

import db_interaction.Executor;

import java.sql.SQLException;

/**
 * Created by kate on 23.04.14.
 */
public class NoSuchUserException extends Exception {
    public NoSuchUserException(String string) {
        super(string);
    }
}
