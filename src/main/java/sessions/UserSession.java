package sessions;

/**
 * Created by kate on 19.04.14.
 */
public class UserSession {
    UserStatus userStatus;
    private long userId;
    private String sessionId;
    private String login;

    public UserSession(String userSessionId, String login) {
        this.sessionId = userSessionId;
        this.login = login;
        this.userId = -1;
        this.userStatus = UserStatus.NOT_AUTHENTICATED_YET;
    }

    public void changeUserName(String login) {
        this.login = login;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}