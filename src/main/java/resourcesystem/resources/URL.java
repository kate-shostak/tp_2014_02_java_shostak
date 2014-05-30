package resourcesystem.resources;

/**
 * Created by kate on 25.05.14.
 */
public class URL implements Resource{

    private String HOME;
    private String LOGIN;
    private String REGISTER;
    private String AUTHFORM;
    private String TIMER_PAGE;
    private String REGISTRATION_FORM;

    public String getLOGIN() {
        return LOGIN;
    }

    public String getREGISTER() {
        return REGISTER;
    }

    public String getAUTHFORM() {
        return AUTHFORM;
    }

    public String getTIMER_PAGE() {
        return TIMER_PAGE;
    }

    public String getREGISTRATION_FORM() {
        return REGISTRATION_FORM;
    }

    public String getHOME() {
        return HOME;
    }
}
