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
<jsp:include page="header.jsp" />



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

<jsp:include page="footer.jspf" />
<script src="js/auction-bid-updates.js"></script>
</body>
</html>
