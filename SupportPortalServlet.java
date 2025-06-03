String name = request.getParameter("name");
name = StringEscapeUtils.escapeHtml4(name);  // Fix for XSS
out.println("<h1>Hello " + name + "</h1>");

String filename = Paths.get(request.getParameter("file")).getFileName().toString(); // Fix for Directory Traversal
File file = new File("/uploads/" + filename);

log.info("User login attempted for user: {}", username);  // Fix for Logging Sensitive Information