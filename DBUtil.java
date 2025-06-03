String username = request.getParameter("username");
String query = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = connection.prepareStatement(query);
pstmt.setString(1, username);
ResultSet rs = pstmt.executeQuery();

String apiKey = System.getenv("STRIPE_API_KEY");