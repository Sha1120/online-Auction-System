<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Distributed Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<nav>
    <a href="home.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/auctions">Auctions</a>
    <a href="register.jsp">Register</a>
    <a href="contact.jsp">Contact</a>
</nav>
<section class="form-section">
    <h2>Login</h2>
    <% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
    <p style="color:red;"><%= message %></p>
    <% } %>

    <form action="LoginServlet" method="post">
        <input type="text" name="username" placeholder="Username or Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit" class="cta-btn">Login</button>
    </form>
    <p>Don't have an account? <a href="register.jsp">Register now</a></p>
</section>
<%@ include file="footer.jspf" %>
</body>
</html>