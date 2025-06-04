<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.auction.ejb.AuctionManager"%>
<%@ page import="jakarta.naming.InitialContext"%>
<%@ page import="com.auction.model.AuctionItem"%>
<%
  long id = Long.parseLong(request.getParameter("id"));
  InitialContext ctx = new InitialContext();
  AuctionManager auctionManager = (AuctionManager) ctx.lookup("java:global/online-Auction-System/AuctionManagerBean!com.auction.ejb.AuctionManager");
  AuctionItem item = null;
  for (AuctionItem ai : auctionManager.getAllAuctions()) {
    if (ai.getId() == id) { item = ai; break; }
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Auction: <%= item.getName() %></title>
  <link rel="stylesheet" href="css/style.css"/>
  <script src="js/auction.js"></script>
</head>
<body>
<h1><%= item.getName() %></h1>
<p><%= item.getDescription() %></p>
<p>Current Bid: $<span id="currentBid"><%= item.getCurrentBid() %></span></p>
<form method="post" action="bid">
  <input type="hidden" name="auctionId" value="<%= item.getId() %>"/>
  <input type="text" name="bidder" placeholder="Your Name" required/>
  <input type="number" step="0.01" min="<%= item.getCurrentBid()+0.01 %>" name="amount" required/>
  <button type="submit">Place Bid</button>
</form>
<p>Auction Ends: <%= item.getEndTime() %></p>
</body>
</html>