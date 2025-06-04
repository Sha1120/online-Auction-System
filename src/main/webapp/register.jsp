<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Distributed Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<nav>
    <a href="home.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/auctions">Auctions</a>
    <a href="login.jsp">Login</a>
    <a href="contact.jsp">Contact</a>
</nav>
<section class="form-section">

    <h2>Register</h2>
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <div class="message"><%= message %></div>
    <%
        }
    %>
    <form action="RegisterServlet" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email" required>
        <div style="position:relative;">
            <input type="password" name="password" placeholder="Password" id="password" required autocomplete="new-password">
            <button type="button" id="togglePassword" aria-label="Show password"
                    style="position:absolute; right:5px; top:50%; transform:translateY(-50%); background:transparent; border:none; cursor:pointer; padding:0;">
            </button>
        </div>
        <div style="position:relative;">
            <input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPassword" required autocomplete="new-password">
            <button type="button" id="toggleConfirmPassword" aria-label="Show password"
                    style="position:absolute; right:5px; top:50%; transform:translateY(-50%); background:transparent; border:none; cursor:pointer; padding:0;">
            </button>
        </div>
        <button type="submit" class="cta-btn">Register</button>
    </form>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</section>
<%@ include file="footer.jspf" %>
</body>
</html>