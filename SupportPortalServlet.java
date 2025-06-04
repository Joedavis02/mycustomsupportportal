import java.util.Scanner;
import java.nio.file.Paths;
import org.apache.commons.text.StringEscapeUtils;

public class SupportPortalServlet extends HttpServlet {

    private static final String SUPPORT_BOT_API_KEY = System.getenv("SUPPORT_BOT_API_KEY");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        name = StringEscapeUtils.escapeHtml4(name);
        out.println("<h1>Hello " + name + "</h1>");

        String filename = Paths.get(request.getParameter("file")).getFileName().toString();
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Agent login attempt: Username = " + username);

        response.setContentType("text/html");
        response.getWriter().println("<p>Login submitted. Check logs for debug info.</p>");
    }
}