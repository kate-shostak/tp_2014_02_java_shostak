package messagesystem;

import dbwiththreads.UserDataSet;
import frontend.FrontendWithThreads;
import sessions.UserStatus;

/**
 * Created by kate on 21.04.14.
 */
public class MessageToUpdateUserId extends MessageToFrontend {
    private UserDataSet result;
    private String sessionId;
    UserStatus userStatus;

    public MessageToUpdateUserId(Address receivedFrom, Address sendTo, UserDataSet result, String sessionId, UserStatus userStatus) {
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
