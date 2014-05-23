package sessions;

/**
 * Created by kate on 23.04.14.
 */
public class UserStatus {
    //enum
    public final static int AUTHENTICATED_USER = 1;
    public final static int NOT_AUTHENTICATED_YET = 2;
    public final static int NO_SUCH_USER_REGISTERED = 3;
    public final static int REPEATED_LOGIN = 4;
    public final static int FRESHER = 5;
    public final static int REGISTERED_OK = 6;
}
