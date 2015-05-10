import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Author: Fisher Evans
 * Date: 3/24/14
 */
public class Root extends Route {
    protected Root() {
        super("/");
    }

    @Override
    public Object handle(Request request, Response response) {
        try {
            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement .executeQuery("select * from listr.users");
            String out = "";
            int id = 1;
            while(resultSet.next()) {
                out += "User " + (id++) + "\n----------\n";
                out += "Username: " + resultSet.getString("username") + "\n";
                out += "Email:    " + resultSet.getString("email") + "\n";
                out += "Name:     " + resultSet.getString("first_name") + " " + resultSet.getString("last_name") + "\n";
                out += "\n\n";
            }
            return out;
        } catch(Exception e) {
            return "error - " + e.toString();
        }
    }
}
