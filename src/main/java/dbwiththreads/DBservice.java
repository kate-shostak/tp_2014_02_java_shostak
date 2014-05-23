package dbwiththreads;

import interfaces.Abonent;
import messagesystem.Address;
import messagesystem.MessageManager;
import messagesystem.TimeHelper;

import java.sql.Connection;
import java.sql.SQLException;
//redo not to touch my usersDao

/**
 * Created by kate on 21.04.14.
 */
public class DBservice implements Abonent, Runnable {
    //private Connection con;
    private MessageManager messageManager;
    private Address dbAddress;
    public UsersDAO usersDAO;

    public DBservice(Connection connection, MessageManager messageManager) {
        this.messageManager = messageManager;
        this.dbAddress = new Address();
        messageManager.addService(this);
        messageManager.getAddressService().setDbServiceAddress(this);
        this.usersDAO = new UsersDAO(connection);
    }

    public Address getAddress() {
        return dbAddress;
    }

    public MessageManager getMessageManager() {
        return messageManager;
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
