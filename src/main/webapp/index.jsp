<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <!-- For icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<jsp:include page="header.jsp" />

<section class="hero">
    <h2>Welcome to the Future of Auctions</h2>
    <p>Discover exclusive items, place your bids, and win amazing deals—all from the comfort of your home.</p>

    <%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%
        String redirectPage;
        if (session.getAttribute("username") != null) {
            redirectPage = "auctions";
        } else {
            redirectPage = "login.jsp";
        }
    %>
    <!-- ... in your HTML ... -->
    <button class="cta-btn" onclick="window.location.href='<%= redirectPage %>'">Start Bidding Now</button>

</section>

<section class="features">
    <div class="feature-card">
        <i class="fas fa-gavel"></i>
        <h3>Live Bidding</h3>
        <p>Experience real-time competitive bidding and never miss an opportunity to win your favorite items.</p>
    </div>
    <div class="feature-card">
        <i class="fas fa-shield-alt"></i>
        <h3>Secure Transactions</h3>
        <p>Your payments and personal data are protected with advanced security measures.</p>
    </div>
    <div class="feature-card">
        <i class="fas fa-clock"></i>
        <h3>24/7 Auctions</h3>
        <p>Bid anytime, anywhere—our platform is always open for you.</p>
    </div>
    <div class="feature-card">
        <i class="fas fa-users"></i>
        <h3>User Community</h3>
        <p>Join a vibrant and growing community of buyers and sellers.</p>
    </div>
</section>

<footer>
    &copy; 2025 Online Auction System. All rights reserved.
</footer>

</body>
</html>