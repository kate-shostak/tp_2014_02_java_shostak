package dbwiththreads;

import interfaces.Abonent;
import messagesystem.Address;
import messagesystem.MessageManager;
import messagesystem.TimeHelper;

import java.sql.SQLException;

/**
 * Created by kate on 21.04.14.
 */
public class DBservice implements Abonent {
    private MessageManager messageManager;
    private Address dbAddress;

    public DBservice(MessageManager messageManager) {
        this.messageManager = messageManager;
        this.dbAddress = new Address();
        messageManager.addService(this);
    }

    public Address getAddress() {
        return dbAddress;
    }

    
}
