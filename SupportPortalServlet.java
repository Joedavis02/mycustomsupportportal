String filename = Paths.get(request.getParameter("file")).getFileName().toString(); // prevents traversal
File file = new File("/uploads/" + filename);