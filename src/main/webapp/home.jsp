<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Home - Distributed Online Auction System</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<header>
  <h1>Distributed Online Auction System</h1>
  <p>Real-Time. Scalable. Reliable.</p>
  <nav>
    <%
      String username = (String) session.getAttribute("username");
      if (username != null) {
    %>
    <p>Welcome, <strong><%= username %></strong>!</p>
    <%
      }
    %>

    <a href="home.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/auctions">Auctions</a>
    <a href="contact.jsp">Contact</a>

    <%
      if (username != null) {
    %>
    <a href="LogoutServlet">Logout</a>
    <%
    } else {
    %>
    <a href="login.jsp">Login</a>
    <%
      }
    %>
  </nav>

</header>

<section class="welcome">
  <h2>Welcome to the Future of Online Auctions</h2>
  <p>
    Participate in live auctions and place your bids in real-time, no matter where you are. <br>
    Our distributed system ensures that your bids are processed instantly and fairly, keeping you at the heart of the action!
  </p>
</section>

<section class="system-details">
  <div class="detail-card">
    <i class="fas fa-network-wired"></i>
    <h3>Distributed Architecture</h3>
    <p>
      Built with scalable EJB modules and JMS messaging for fast, reliable, and synchronized live auctions across the globe.
    </p>
  </div>
  <div class="detail-card">
    <i class="fas fa-bolt"></i>
    <h3>Real-Time Updates</h3>
    <p>
      All bids and auction status are updated in real-time using asynchronous messaging, so you never miss a moment!
    </p>
  </div>
  <div class="detail-card">
    <i class="fas fa-shield-alt"></i>
    <h3>Robust Business Logic</h3>
    <p>
      Advanced EJB-driven business logic ensures valid bidding, accurate timing, and automatic bid increments.
    </p>
  </div>
</section>

<section class="technologies">
  <h3>Powered By</h3>
  <ul>
    <li>Enterprise JavaBeans (EJB) for modular business logic</li>
    <li>Java Message Service (JMS) for asynchronous, distributed communication</li>
    <li>Modern Java EE Application Server (e.g., Payara, GlassFish)</li>
    <li>Responsive Web Interface for seamless user experience</li>
  </ul>
</section>

<footer>
  &copy; 2025 Distributed Online Auction System. All rights reserved.
</footer>

</body>
</html>
