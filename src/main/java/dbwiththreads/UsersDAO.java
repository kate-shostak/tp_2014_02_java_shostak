package dbwiththreads;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.NoSuchUserException;
import exception.RepeatedLoginException;
import interfaces.Abonent;
import interfaces.ResultHandler;
import messagesystem.Address;
import messagesystem.MessageManager;
import messagesystem.TimeHelper;

/**
 * Created by kate on 25.03.14.
 */
public class UsersDAO implements Abonent, Runnable {

    private Connection con;
    private MessageManager messageManager;
    private Address dbAddress;
    Executor executor = new Executor();
    String log;
    String pass;

    public UsersDAO(Connection connection, MessageManager messageManager) {
        this.con = connection;
        this.messageManager = messageManager;
        this.dbAddress = new Address();
        messageManager.addService(this);
        messageManager.getAddressService().setDbServiceAddress(this);
    }

    public Address getAddress() {
        return dbAddress;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public UserDataSet getById(long id) throws SQLException, NoSuchUserException {
        return executor.execQuery(con, "select * from users where id=" + id, new ResultHandler<UserDataSet>() {
            public UserDataSet handle(ResultSet result) throws SQLException {
                result.next();
                return new UserDataSet(result.getInt(1), result.getString(2), result.getString(3));
            }
        });
    }

    //HANDLE PASSWORDS WITH REAL CRYPTO-METHODS.
    public UserDataSet getBylogin(String login) throws SQLException, NoSuchUserException {
        return executor.execQuery(con, "select * from users where login=" + "'" + login + "'", new ResultHandler<UserDataSet>() {
            public UserDataSet handle(ResultSet result) throws SQLException, NoSuchUserException {
                if (result.next())
                    return new UserDataSet(result.getInt(1), result.getString(2), result.getString(3));
                else {
                    throw new NoSuchUserException("No such user in DB");
                }
            }
        });

    }

    //should we better use exceptions or just logical interlation..
    //enum
    public void AddUser(UserDataSet newUser) throws SQLException, RepeatedLoginException {
        log = newUser.getLogin();
        pass = newUser.getPassword();
        try {
            getBylogin(log);
            throw new RepeatedLoginException(log);
        } catch (NoSuchUserException e) {
            executor.execUpdate(con, "insert into users (login, password) values (" + "'" + log + "'" + "," + "'" + pass + "'" + ")");
        }
    }

    public void run() {
        while (true) {
            try {
                messageManager.executeForAbonent(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TimeHelper.sleep(100);
        }
    }
}