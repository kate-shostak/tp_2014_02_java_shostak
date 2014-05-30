package dbwiththreads;

import resourcesystem.resources.dbConnection;
import resourcesystem.resources.resourceManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kate on 25.03.14.
 */
public class MyConnector {

    //Static- so the instance is single for everyone.
    public static Connection setConnection() {
        try {
            //Driver reflection registration and db_connection establishing
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            dbConnection connectionXML = (dbConnection) resourceManager.getInstance().getResource("data/dbConnection.xml");
            String url = connectionXML.getDB();
            String name = connectionXML.getNAME();
            String password = connectionXML.getPASSWORD();
            StringBuilder completeUrl = new StringBuilder();
            completeUrl.append(url).append("?").append("user=").append(name).append("&").append("password=").append(password);
            Connection connection = DriverManager.getConnection(completeUrl.toString());
            System.out.println("DB_connection established!");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
