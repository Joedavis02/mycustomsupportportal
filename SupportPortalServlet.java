String name = request.getParameter("name");
name = StringEscapeUtils.escapeHtml4(name);  // From Apache Commons Text
out.println("<h1>Hello " + name + "</h1>");

String filename = Paths.get(request.getParameter("file")).getFileName().toString(); // prevents traversal
File file = new File("/uploads/" + filename);

log.info("User login attempted for user: {}", username);  // Avoid logging sensitive data