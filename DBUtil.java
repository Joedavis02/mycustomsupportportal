import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getConnection() {
        try {
            // Secure: Load DB credentials from environment variables
            String url = System.getenv("SUPPORT_DB_URL");
            String user = System.getenv("SUPPORT_DB_USER");
            String pass = System.getenv("SUPPORT_DB_PASS");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
