import spark.Request;
import spark.Response;
import spark.Route;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Author: Fisher Evans
 * Date: 3/24/14
 */
public class Login extends Route {
    protected Login() {
        super("/login");
    }

    @Override
    public Object handle(Request request, Response response) {
        return "login";
    }
}
