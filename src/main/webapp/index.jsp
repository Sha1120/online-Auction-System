<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.auction.ejb.AuctionManager"%>
<%@ page import="jakarta.naming.InitialContext"%>
<%@ page import="java.util.List"%>
<%@ page import="com.auction.model.AuctionItem"%>
<%
    InitialContext ctx = new InitialContext();
    AuctionManager auctionManager = (AuctionManager) ctx.lookup("java:global/online-Auction-System/AuctionManagerBean!com.auction.ejb.AuctionManager");
    List<AuctionItem> auctions = auctionManager.getAllAuctions();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Online Auction Home</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<h1>Live Auctions</h1>
<table>
    <tr><th>Item</th><th>Current Bid</th><th>Ends At</th><th>Action</th></tr>
    <% for(AuctionItem item : auctions) { %>
    <tr>
        <td><%= item.getName() %></td>
        <td>$<%= item.getCurrentBid() %></td>
        <td><%= item.getEndTime() %></td>
        <td>
            <form action="auction.jsp" method="get">
                <input type="hidden" name="id" value="<%= item.getId() %>"/>
                <button type="submit">Bid</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>