package messagesystem;

import dbwiththreads.DBservice;
import interfaces.Abonent;

import java.sql.SQLException;

/**
 * Created by kate on 19.04.14.
 */
public abstract class MessageToDataBase extends Message {
    public MessageToDataBase(Address recievedFrom, Address sendTo) {
        super(recievedFrom, sendTo);
    }

    void execute(Abonent abonent) throws SQLException {
        if (abonent instanceof DBservice) execute((DBservice) abonent);
    }

    abstract void execute(DBservice dataBaseWithThreads) throws SQLException;
}
