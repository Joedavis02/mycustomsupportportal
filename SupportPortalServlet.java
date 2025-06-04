import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import org.apache.commons.text.StringEscapeUtils;
import java.util.logging.Logger;

public class SupportPortalServlet {

    private static final Logger log = Logger.getLogger(SupportPortalServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Acme Customer Support Portal</h1>");

        // XSS Fix: Escape user input
        String name = request.getParameter("name");
        if (name != null) {
            name = StringEscapeUtils.escapeHtml4(name);
            out.println("<p>Logged in as: <strong>" + name + "</strong></p>");
        }

        // Directory Traversal Fix: Validate file paths
        String filename = request.getParameter("file");
        if (filename != null) {
            filename = Paths.get(filename).getFileName().toString();
            File file = new File("/uploads/" + filename);
            try (Scanner scanner = new Scanner(file)) {
                out.println("<h3>Attachment Content:</h3><pre>");
                while (scanner.hasNextLine()) {
                    out.println(scanner.nextLine());
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
        // Avoid logging sensitive information
        String username = request.getParameter("username");
        log.info("User login attempted for user: " + username);

        response.setContentType("text/html");
        response.getWriter().println("<p>Login submitted. Check logs for debug info.</p>");
    }
}