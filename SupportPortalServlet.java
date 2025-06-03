String name = request.getParameter("name");
name = StringEscapeUtils.escapeHtml4(name);  // From Apache Commons Text
out.println("<h1>Hello " + name + "</h1>");