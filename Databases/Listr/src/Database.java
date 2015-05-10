import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Fisher Evans
 * Date: 3/24/14
 */
public class Database {
    public static Connection connection;

    public static void load() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306";
            String username = "root";
            String password = "root";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
