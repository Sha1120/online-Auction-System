<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Distributed Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body id="register-body">
<jsp:include page="header.jsp" />
<div id="register-div-form">

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
        <div class="register-password-wrapper">
            <input type="password" name="password" placeholder="Password" id="password" required autocomplete="new-password">
            <button type="button" id="togglePassword" aria-label="Show password" class="register-password-toggle">
                <i class="fa fa-eye"></i>
            </button>
        </div>
        <div class="register-password-wrapper">
            <input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPassword" required autocomplete="new-password">
            <button type="button" id="toggleConfirmPassword" aria-label="Show password" class="register-password-toggle">
                <i class="fa fa-eye"></i>
            </button>
        </div>
        <button type="submit" class="cta-btn">Register</button>
    </form>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</div>
<%@ include file="footer.jspf" %>
<!-- Password toggle JS -->
<script>
    document.getElementById('togglePassword').onclick = function() {
        const pwd = document.getElementById('password');
        const icon = this.querySelector('i');
        if (pwd.type === 'password') {
            pwd.type = 'text';
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            pwd.type = 'password';
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    };
    document.getElementById('toggleConfirmPassword').onclick = function() {
        const pwd = document.getElementById('confirmPassword');
        const icon = this.querySelector('i');
        if (pwd.type === 'password') {
            pwd.type = 'text';
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            pwd.type = 'password';
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    };
</script>
</body>
</html>