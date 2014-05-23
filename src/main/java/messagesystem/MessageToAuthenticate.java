package messagesystem;

import dbwiththreads.UserDataSet;
import dbwiththreads.UsersDAO;
import exception.NoSuchUserException;
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

    void execute(UsersDAO dataBaseWithThreads) {
        UserDataSet result;
        try {
            result = dataBaseWithThreads.getBylogin(userDataSet.getLogin());
            dataBaseWithThreads.getMessageManager().sendMessage(new MessageToUpdateUserId(getReciever(), getSender(), result, sessionId, UserStatus.AUTHENTICATED_USER));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchUserException e) {
            dataBaseWithThreads.getMessageManager().sendMessage(new ExceptionHandlingMessage(getReciever(), getSender(), UserStatus.NO_SUCH_USER_REGISTERED, sessionId));
        }
    }
}
