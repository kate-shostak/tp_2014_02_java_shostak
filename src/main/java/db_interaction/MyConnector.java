package db_interaction;

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
            String url = "jdbc:mysql://localhost:3306/java_db?user=lady&password=bird";
            Connection connection = DriverManager.getConnection(url);
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
