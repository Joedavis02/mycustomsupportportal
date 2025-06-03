import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import org.apache.commons.text.StringEscapeUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SupportPortalServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        name = StringEscapeUtils.escapeHtml4(name); // Escape output to prevent XSS
        response.getWriter().println("<h1>Hello " + name + "</h1>");

        String filename = Paths.get(request.getParameter("file")).getFileName().toString(); // Sanitize file paths
        File file = new File("/uploads/" + filename);
        try (Scanner scanner = new Scanner(file)) {
            response.getWriter().println("<h3>File Content:</h3><pre>");
            while (scanner.hasNextLine()) {
                response.getWriter().println(scanner.nextLine());
            }
            response.getWriter().println("</pre>");
        } catch (Exception e) {
            response.getWriter().println("<p>Attachment not found.</p>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Agent login attempt: Username = " + username); // Avoid logging sensitive data
        response.setContentType("text/html");
        response.getWriter().println("<p>Login submitted. Check logs for debug info.</p>");
    }
}