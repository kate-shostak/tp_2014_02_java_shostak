package messagesystem;

import interfaces.Abonent;

import java.sql.SQLException;

/**
 * Created by kate on 19.04.14.
 */
public abstract class Message {
    Address recievedFrom;
    Address sendTo;

    public Message(Address recievedFrom, Address sendTo) {
        this.recievedFrom = recievedFrom;
        this.sendTo = sendTo;
    }

    Address getReciever() {
        return sendTo;
    }

    Address getSender() {
        return recievedFrom;
    }

    abstract void execute(Abonent abonent) throws SQLException;

}
