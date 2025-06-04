import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.text.StringEscapeUtils;

public class SupportPortalServlet extends HttpServlet {

    private static final String SUPPORT_BOT_API_KEY = System.getenv("SUPPORT_BOT_API_KEY");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Acme Customer Support Portal</h1>");

        String agent = request.getParameter("agent");
        if (agent != null) {
            agent = StringEscapeUtils.escapeHtml4(agent);
            out.println("<p>Logged in as: <strong>" + agent + "</strong></p>");
        }

        String email = request.getParameter("email");
        if (email != null) {
            out.println("<h3>Search Results for Email: " + StringEscapeUtils.escapeHtml4(email) + "</h3>");
            try (Connection conn = DBUtil.getConnection()) {
                String query = "SELECT * FROM customers WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    out.println("<p>Customer Name: " + rs.getString("name") + "</p>");
                    out.println("<p>Email: " + rs.getString("email") + "</p>");
                }
            } catch (Exception e) {
                out.println("<p>Error retrieving customer data.</p>");
            }
        }

        String fileParam = request.getParameter("attachment");
        if (fileParam != null) {
            String filename = Paths.get(fileParam).getFileName().toString();
            File file = new File("/var/acme/uploads/" + filename);
            try (Scanner scanner = new Scanner(file)) {
                out.println("<h3>Attachment Content:</h3><pre>");
                while (scanner.hasNextLine()) {
                    out.println(StringEscapeUtils.escapeHtml4(scanner.nextLine()));
                }
                out.println("</pre>");
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Agent login attempt: Username = " + username);

        response.setContentType("text/html");
        response.getWriter().println("<p>Login submitted. Check logs for debug info.</p>");
    }
}