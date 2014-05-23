package interfaces;

import exception.NoSuchUserException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kate on 25.03.14.
 */
public interface ResultHandler<T> {
    T handle(ResultSet result) throws SQLException, NoSuchUserException;
}
