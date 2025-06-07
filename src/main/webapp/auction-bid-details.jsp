<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Auction Bid Detail</title>
</head>
<body>
<h1 id="auctionTitle"><%= request.getAttribute("auctionTitle") != null ? request.getAttribute("auctionTitle") : "Auction Title" %></h1>
<p>Description: <%= request.getAttribute("auctionDescription") != null ? request.getAttribute("auctionDescription") : "" %></p>
<p>Current Price: $<span id="currentBid"><%= request.getAttribute("currentBid") != null ? request.getAttribute("currentBid") : "0.00" %></span></p>

<!-- Container for live bid notifications -->
<div id="bid-notifications">
  <h3>Live Bid Updates</h3>
</div>

<!-- ... existing bid placement form ... -->
<form action="PlaceBidServlet" method="post">
  <input type="hidden" name="auctionId" value="${auction.id}" />
  <input type="number"
         name="bidAmount"
         min="${auction.currentPrice + 1}"
         required
         placeholder="Your Bid"
         <c:if test="${auction.timeRemaining == 'Auction ended'}">disabled</c:if> />
  <button type="submit" class="cta-btn"
          <c:if test="${auction.timeRemaining == 'Auction ended'}">disabled</c:if>>
    Place Bid
  </button>
</form>

<!-- Link the WebSocket JS file -->
<script src="js/auction-bid-updates.js"></script>
</body>
</html>