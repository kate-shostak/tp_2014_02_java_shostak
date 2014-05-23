package messagesystem;

import frontend.FrontendWithThreads;

/**
 * Created by kate on 23.04.14.
 */
public class ExceptionHandlingMessage extends MessageToFrontend {
    private int userStatus;
    private String sessionId;

    public ExceptionHandlingMessage(Address receivedFrom, Address sendTo, int userStatus, String sessionId) {
        super(receivedFrom, sendTo);
        this.userStatus = userStatus;
        this.sessionId = sessionId;
    }

    public void execute(FrontendWithThreads frontendWithThreads) {
        frontendWithThreads.setUserStatus(sessionId, userStatus);
    }
}
