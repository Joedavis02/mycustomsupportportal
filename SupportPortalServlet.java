import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

public class SupportPortalServlet {

    protected void doGet(HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            String query = "SELECT * FROM users WHERE username = ?";

            Connection connection = DBUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("User: " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}