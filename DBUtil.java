String username = request.getParameter("username");
String query = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = connection.prepareStatement(query);
pstmt.setString(1, username);
ResultSet rs = pstmt.executeQuery();

String name = request.getParameter("name");
name = StringEscapeUtils.escapeHtml4(name);
out.println("<h1>Hello " + name + "</h1>");

String apiKey = System.getenv("STRIPE_API_KEY");

String filename = Paths.get(request.getParameter("file")).getFileName().toString();
File file = new File("/uploads/" + filename);

log.info("User login attempted for user: {}", username);