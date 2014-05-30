package messagesystem;

import frontend.FrontendWithThreads;
import sessions.UserStatus;

/**
 * Created by kate on 23.04.14.
 */
public class ExceptionHandlingMessage extends MessageToFrontend {
    UserStatus userStatus;
    private String sessionId;

    public ExceptionHandlingMessage(Address receivedFrom, Address sendTo, UserStatus userStatus, String sessionId) {
        super(receivedFrom, sendTo);
        this.userStatus = userStatus;
        this.sessionId = sessionId;
    }

    public void execute(FrontendWithThreads frontendWithThreads) {
        frontendWithThreads.setUserStatus(sessionId, userStatus);
    }
}
