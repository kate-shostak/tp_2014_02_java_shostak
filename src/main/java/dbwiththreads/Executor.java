package dbwiththreads;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kate on 25.03.14.
 */
public class Executor {

    public <T> T execQuery(Connection connection, String query, ResultHandler<T> handler) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        T value = handler.handle(resultSet);
        resultSet.close();
        statement.close();
        return value;
    }

    public void execUpdate(Connection connection, String update) throws SQLException {
        Statement stmnt = connection.createStatement();
        stmnt.execute(update);
        stmnt.close();
    }
}
