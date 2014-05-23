package dbwiththreads;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kate on 25.03.14.
 */
public class UsersDAO {

    private Connection con;
    dbwiththreads.Executor executor = new Executor();
    String log;
    String pass;

    public UsersDAO(Connection connection) {
        this.con = connection;
    }

    //Handle Exceptions somehow...
    public UserDataSet getById(long id) throws SQLException {
        return executor.execQuery(con, "select * from users where id=" + id, new ResultHandler<UserDataSet>() {
            public UserDataSet handle(ResultSet result) throws SQLException {
                result.next();
                return new dbwiththreads.UserDataSet(result.getInt(1), result.getString(2), result.getString(3));
            }
        });
    }

    //And here too.
    public dbwiththreads.UserDataSet getBylogin(String login) throws SQLException {
        return executor.execQuery(con, "select * from users where login=" + "'" + login + "'", new ResultHandler<UserDataSet>() {
            public dbwiththreads.UserDataSet handle(ResultSet result) throws SQLException {
                result.next();
                return new UserDataSet(result.getInt(1), result.getString(2), result.getString(3));
            }
        });
    }

    public void AddUser(UserDataSet newUser) throws SQLException {
            log = newUser.getLogin();
            pass = newUser.getPassword();
            executor.execUpdate(con, "insert into users (login, password) values (" + "'" + log + "'" + "," + "'" + pass + "'" + ")");
    }

}