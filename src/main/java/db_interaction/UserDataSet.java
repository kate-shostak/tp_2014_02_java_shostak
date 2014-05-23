package db_interaction;

/**
 * Created by kate on 25.03.14.
 */
public class UserDataSet {

    private Integer id;
    private String login;
    private String password;

    //The new user constructor.
    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    //The constructor to execute Query and Update statements.
    public UserDataSet(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return this.id;
    }

}
