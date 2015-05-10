import static spark.Spark.*;
import spark.*;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Author: Fisher Evans
 * Date: 3/24/14
 */
public class Router {
    public static void main(String[] args) {
        Database.load();

        setPort(6393);
        get(new Root());
        get(new Login());
    }
}
