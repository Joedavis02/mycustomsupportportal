import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.regex.*;

public class SupportPortalServlet extends HttpServlet {

    private static final String SUPPORT_BOT_API_KEY = System.getenv("SUPPORT_BOT_API_KEY");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Acme Customer Support Portal</h1>");

        String email = request.getParameter("email");
        if (email != null && isValidEmail(email)) {
            out.println("<h3>Search Results for Email: " + email + "</h3>");
            try (Connection conn = DBUtil.getConnection()) {
                String query = "SELECT * FROM customers WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, email);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            out.println("<p>Customer Name: " + rs.getString("name") + "</p>");
                            out.println("<p>Email: " + rs.getString("email") + "</p>");
                        }
                    }
                }
            } catch (SQLException e) {
                out.println("<p>Error retrieving customer data.</p>");
            }
        } else {
            out.println("<p>Invalid or missing email parameter.</p>");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}