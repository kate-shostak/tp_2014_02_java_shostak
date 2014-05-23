package sessions;

import javax.servlet.http.HttpSession;

/**
 * Created by kate on 19.04.14.
 */
public class UserSession {
    private int userStatus;
    private long userId;
    private String sessionId;
    private String login;

    public UserSession(String userSessionId, String login) {
        this.sessionId = userSessionId;
        this.login = login;
        this.userId = -1;
        this.userStatus = UserStatus.NOT_AUTHENTICATED_YET;
    }

    public String getUserName() {
        return login;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}