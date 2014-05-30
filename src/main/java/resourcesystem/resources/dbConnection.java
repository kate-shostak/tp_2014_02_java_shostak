package resourcesystem.resources;


import javax.annotation.*;
import java.io.Serializable;

/**
 * Created by kate on 31.05.14.
 */

public class dbConnection implements Resource {
    int PORT;
    String DB;
    String NAME;
    String PASSWORD;

    public String getDB() {
        return DB;
    }

    public int getPORT () {
        return PORT;
    }
    public String getNAME () {
        return NAME;
    }

    public String getPASSWORD() {
        return  PASSWORD;
    }
}
