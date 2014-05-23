package messagesystem;

import dbwiththreads.UsersDAO;
import frontend.FrontendWithThreads;

/**
 * Created by kate on 25.04.14.
 */
public class MessageToUpdateUserStatus extends MessageToFrontend {
    int userStatus;
    String sessionId;

    MessageToUpdateUserStatus(Address recievedFrom, Address sendTo, String sessionId, int userStatus) {
        super(recievedFrom, sendTo);
        this.sessionId = sessionId;
        this.userStatus = userStatus;
    }

    void execute(FrontendWithThreads frontendWithThreads) {
        frontendWithThreads.setUserStatus(sessionId, userStatus);
    }
}
