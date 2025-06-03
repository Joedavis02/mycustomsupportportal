import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SupportPortalServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        name = StringEscapeUtils.escapeHtml4(name); // Escaping HTML to prevent XSS
        response.getWriter().println("<h1>Hello " + name + "</h1>");

        String email = request.getParameter("email");
        if (email != null) {
            response.getWriter().println("<h3>Search Results for Email: " + email + "</h3>");
            Connection conn = DBUtil.getConnection();
            try {
                String query = "SELECT * FROM customers WHERE email = ?"; // Using PreparedStatement to prevent SQL Injection
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    response.getWriter().println("<p>Customer Name: " + rs.getString("name") + "</p>");
                    response.getWriter().println("<p>Email: " + rs.getString("email") + "</p>");
                }
            } catch (Exception e) {
                response.getWriter().println("<p>Error retrieving customer data.</p>");
            }
        }
    }
}