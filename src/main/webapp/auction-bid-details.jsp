<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Auction Bid Detail</title>
</head>
<body>

<h1>Auction Detail Page</h1>

<!-- Auction info display -->
<div id="auction-info">
  <h2 id="auction-title">Auction Title</h2>
  <p id="auction-description">Description goes here...</p>
  <p id="auction-current-price">Current Price: $<span id="price">0.00</span></p>
</div>

<!-- Live bid updates -->
<div id="bids">
  <h3>Live Bids Updates</h3>
</div>

<script>
  const socket = new WebSocket('ws://localhost:8080/online-Auction-System/bidUpdates');

  socket.onopen = function () {
    console.log('WebSocket connected');
  };

  socket.onmessage = function (event) {
    console.log('Bid Update:', event.data);

    const bidsDiv = document.getElementById('bids');
    const p = document.createElement('p');
    p.textContent = event.data;
    bidsDiv.appendChild(p);

    // Extract price and update UI
    const priceMatch = event.data.match(/\$([\d.]+)/);
    if (priceMatch) {
      const newPrice = priceMatch[1];
      document.getElementById('price').textContent = newPrice;
    }

    // Extract title and update UI
    const titleMatch = event.data.match(/bid on (.+?):/);
    if (titleMatch) {
      const newTitle = titleMatch[1];
      document.getElementById('auction-title').textContent = newTitle;
    }
  };

  socket.onclose = function () {
    console.log('WebSocket disconnected');
  };

  socket.onerror = function (error) {
    console.error('WebSocket error:', error);
  };
</script>

</body>
</html>
