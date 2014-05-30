package dbwiththreads;

import interfaces.ResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kate on 25.03.14.
 */
public class UsersDAO {

    private Connection con;
    private UserDataSet userDataSet;
    Executor executor = new Executor();
    String log;
    String pass;

    public UsersDAO(Connection connection) {
        this.con = connection;
    }


    public UserDataSet getById(long id) throws SQLException {
        return executor.execQuery(con, "select * from users where id=" + id, new ResultHandler<UserDataSet>() {
            public UserDataSet handle(ResultSet result) throws SQLException {
                result.next();
                return new UserDataSet(result.getInt(1), result.getString(2), result.getString(3));
            }
        });
    }

    //HANDLE PASSWORDS WITH REAL CRYPTO-METHODS.
    public UserDataSet getBylogin(String login) throws SQLException {
        return executor.execQuery(con, "select * from users where login=" + "'" + login + "'", new ResultHandler<UserDataSet>() {
            public UserDataSet handle(ResultSet result) throws SQLException {
                if (result.next())
                    return new UserDataSet(result.getInt(1), result.getString(2), result.getString(3));
                else {
                    return null;
                }
            }
        });

    }

    //should we better use exceptions or just logical interlation..
    //enum
    public boolean AddUser(UserDataSet newUser) throws SQLException {
        log = newUser.getLogin();
        pass = newUser.getPassword();
        userDataSet = getBylogin(log);
        if (userDataSet != null) {
            return false;
        } else {
            executor.execUpdate(con, "insert into users (login, password) values (" + "'" + log + "'" + "," + "'" + pass + "'" + ")");
            return true;
        }
    }

}