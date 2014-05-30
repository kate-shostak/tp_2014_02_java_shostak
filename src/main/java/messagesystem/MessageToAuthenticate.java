package messagesystem;

import dbwiththreads.DBservice;
import dbwiththreads.UserDataSet;
import sessions.UserStatus;

import java.sql.SQLException;

/**
 * Created by kate on 20.04.14.
 */
public class MessageToAuthenticate extends MessageToDataBase {
    private UserDataSet userDataSet;
    public String sessionId;


    public MessageToAuthenticate(Address recievedFrom, Address sendTo, String login, String password, String sessionId) {
        super(recievedFrom, sendTo);
        userDataSet = new UserDataSet(login, password);
        System.out.println(sendTo);
        this.sessionId = sessionId;
    }

    void execute(DBservice dataBaseWithThreads) {
        UserDataSet result;
        try {
            result = dataBaseWithThreads.usersDAO.getBylogin(userDataSet.getLogin());
            if (result != null &&  result.getPassword().equals(userDataSet.getPassword())) {
                dataBaseWithThreads.getMessageManager().sendMessage(new MessageToUpdateUserId(getReciever(), getSender(), result, sessionId, UserStatus.AUTHENTICATED_USER));
            }
            else
                dataBaseWithThreads.getMessageManager().sendMessage(new ExceptionHandlingMessage(getReciever(), getSender(), UserStatus.NO_SUCH_USER_REGISTERED, sessionId));
        } catch (SQLException e) {
            e.printStackTrace();


        }
    }
}
