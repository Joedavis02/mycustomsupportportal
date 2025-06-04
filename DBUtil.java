import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBUtil {

    public static Connection getConnection() {
        try {
            // Securely retrieve credentials from environment variables
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}