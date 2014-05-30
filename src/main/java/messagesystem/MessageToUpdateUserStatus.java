package messagesystem;

import frontend.FrontendWithThreads;
import sessions.UserStatus;

/**
 * Created by kate on 25.04.14.
 */
public class MessageToUpdateUserStatus extends MessageToFrontend {
    UserStatus userStatus;
    String sessionId;

    MessageToUpdateUserStatus(Address recievedFrom, Address sendTo, String sessionId, UserStatus userStatus) {
        super(recievedFrom, sendTo);
        this.sessionId = sessionId;
        this.userStatus = userStatus;
    }

    void execute(FrontendWithThreads frontendWithThreads) {
        frontendWithThreads.setUserStatus(sessionId, userStatus);
    }
}
