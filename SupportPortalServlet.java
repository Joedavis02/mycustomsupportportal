import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import org.apache.commons.text.StringEscapeUtils;
import java.nio.file.Paths;

public class SupportPortalServlet extends HttpServlet {
    // Secure: Load API secret from environment variable
    private static final String SUPPORT_BOT_API_KEY = System.getenv("SUPPORT_BOT_API_KEY");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Acme Customer Support Portal</h1>");

        // XSS Remediation: Escape user-supplied 'agent' name
        String agent = request.getParameter("agent");
        if (agent != null) {
            String safeAgent = StringEscapeUtils.escapeHtml4(agent);
            out.println("<p>Logged in as: <strong>" + safeAgent + "</strong></p>");
        }

        // SQL Injection Remediation: Use PreparedStatement
        String email = request.getParameter("email");
        if (email != null) {
            out.println("<h3>Search Results for Email: " + email + "</h3>");
            Connection conn = DBUtil.getConnection();
            try {
                String query = "SELECT * FROM customers WHERE email = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    out.println("<p>Customer Name: " + rs.getString("name") + "</p>");
                    out.println("<p>Email: " + rs.getString("email") + "</p>");
                }
            } catch (Exception e) {
                out.println("<p>Error retrieving customer data.</p>");
            }
        }

        // Directory Traversal Remediation: Sanitize and validate file path
        String fileParam = request.getParameter("attachment");
        if (fileParam != null) {
            String safeFileName = Paths.get(fileParam).getFileName().toString();
            File file = new File("/var/acme/uploads/" + safeFileName);
            try {
                Scanner scanner = new Scanner(file);
                out.println("<h3>Attachment Content:</h3><pre>");
                while (scanner.hasNextLine()) {
                    out.println(scanner.nextLine());
                }
                out.println("</pre>");
                scanner.close();
            } catch (Exception e) {
                out.println("<p>Attachment not found.</p>");
            }
        }

        out.println("<hr>");
        out.println("<form method='POST'><h3>Agent Login</h3>");
        out.println("Username: <input name='username'><br>");
        out.println("Password: <input type='password' name='password'><br>");
        out.println("<button type='submit'>Login</button></form>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logging Sensitive Information Vulnerability
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Agent login attempt: Username = " + username + ", Password = " + password);

        response.setContentType("text/html");
        response.getWriter().println("<p>Login submitted. Check logs for debug info.</p>");
    }
}
