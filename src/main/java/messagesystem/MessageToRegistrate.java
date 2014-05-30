package messagesystem;

import dbwiththreads.DBservice;
import dbwiththreads.UserDataSet;
import sessions.UserStatus;

import java.sql.SQLException;

/**
 * Created by kate on 19.04.14.
 */
public class MessageToRegistrate extends MessageToDataBase {
    private UserDataSet userDataSet;
    private String sessionId;

    public MessageToRegistrate(Address receivedFrom, Address sendTo, String login, String password, String sessionId) {
        super(receivedFrom, sendTo);
        userDataSet = new UserDataSet(login, password);
        this.sessionId = sessionId;
    }

    void execute(DBservice dataBaseWithThreads) {
        try {
            if (dataBaseWithThreads.usersDAO.AddUser(userDataSet))
                dataBaseWithThreads.getMessageManager().sendMessage(new MessageToUpdateUserStatus(getReciever(), getSender(), sessionId, UserStatus.REGISTERED_OK));
            else
                dataBaseWithThreads.getMessageManager().sendMessage(new ExceptionHandlingMessage(getReciever(), getSender(), UserStatus.REPEATED_LOGIN, sessionId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
