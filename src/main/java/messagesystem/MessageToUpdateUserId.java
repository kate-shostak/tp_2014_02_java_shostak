package messagesystem;

import dbwiththreads.UserDataSet;
import frontend.FrontendWithThreads;
import interfaces.Abonent;

/**
 * Created by kate on 21.04.14.
 */
public class MessageToUpdateUserId extends MessageToFrontend {
    private UserDataSet result;
    private String sessionId;
    private int userStatus;

    public MessageToUpdateUserId(Address receivedFrom, Address sendTo, UserDataSet result, String sessionId, int userStatus) {
        super(receivedFrom, sendTo);
        this.result = result;
        this.sessionId = sessionId;
        this.userStatus = userStatus;
    }

    public void execute(FrontendWithThreads frontendWithThreads) {
        frontendWithThreads.setId(sessionId, result);
        frontendWithThreads.setUserStatus(sessionId, userStatus);
    }
}
