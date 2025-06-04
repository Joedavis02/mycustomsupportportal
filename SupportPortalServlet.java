import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

public class SupportPortalServlet extends HttpServlet {

    private static final String SUPPORT_BOT_API_KEY = System.getenv("SUPPORT_BOT_API_KEY");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        if (name != null) {
            name = StringEscapeUtils.escapeHtml4(name);
            response.getWriter().println("<h1>Hello " + name + "</h1>");
        }

        String email = request.getParameter("email");
        if (email != null) {
            response.getWriter().println("<h3>Search Results for Email: " + email + "</h3>");
            // Simulated database query (secure implementation)
            response.getWriter().println("<p>Customer data retrieved securely.</p>");
        }

        String fileParam = request.getParameter("attachment");
        if (fileParam != null) {
            String filename = Paths.get(fileParam).getFileName().toString();
            File file = new File("/uploads/" + filename);
            try (Scanner scanner = new Scanner(file)) {
                response.getWriter().println("<h3>Attachment Content:</h3><pre>");
                while (scanner.hasNextLine()) {
                    response.getWriter().println(scanner.nextLine());
                }
                response.getWriter().println("</pre>");
            } catch (IOException e) {
                response.getWriter().println("<p>Attachment not found.</p>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Agent login attempt: Username = " + username);
        response.setContentType("text/html");
        response.getWriter().println("<p>Login submitted. Check logs for debug info.</p>");
    }
}