<%@ page import="java.sql.*, javax.naming.*, javax.sql.DataSource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String userId = (String) session.getAttribute("loggedUserId"); // assume user id is saved during login

    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Context initCtx = new InitialContext();
    DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/auctionDS");
    Connection conn = ds.getConnection();

    // Retrieve user's created auctions
    PreparedStatement auctionStmt = conn.prepareStatement("SELECT * FROM actions WHERE user_id = ?");
    auctionStmt.setString(1, userId);
    ResultSet auctionRs = auctionStmt.executeQuery();

    // Retrieve user's bids
    PreparedStatement bidStmt = conn.prepareStatement(
            "SELECT b.bid_amount, a.title, a.end_date FROM bids b JOIN actions a ON b.actions_id = a.id WHERE b.user_id = ?"
    );
    bidStmt.setString(1, userId);
    ResultSet bidRs = bidStmt.executeQuery();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Bids & Auctions</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body id="my-bids-body">
<jsp:include page="header.jsp" />

<section class="my-section">
    <h2 class="my-section-title">My Created Auctions</h2>
    <div class="my-auction-list">
        <%
            boolean hasAuctions = false;
            while (auctionRs.next()) {
                hasAuctions = true;
        %>
        <div class="my-auction-card">
            <img class="mybid-auction-img" src="<%= request.getContextPath() + "/" + auctionRs.getString("img_url") %>" alt="Auction Image" />
            <div class="my-auction-title"><%= auctionRs.getString("title") %></div>
            <div class="my-auction-detail">
                <span class="my-auction-label">Start Price:</span>
                <span class="my-auction-value">$<%= auctionRs.getDouble("start_price") %></span>
            </div>
            <div class="my-auction-detail">
                <span class="my-auction-label">End Date:</span>
                <span class="my-auction-value"><%= auctionRs.getTimestamp("end_date") %></span>
            </div>
        </div>
        <%
            }
            if (!hasAuctions) {
        %>
        <div class="my-empty-msg">You haven't created any auctions yet.</div>
        <%
            }
        %>
    </div>
</section>

<section class="my-section">
    <h2 class="my-section-title">My Bids</h2>
    <div class="my-bid-list">
        <%
            boolean hasBids = false;
            while (bidRs.next()) {
                hasBids = true;
        %>
        <div class="my-bid-card">
            <div class="my-bid-title"><%= bidRs.getString("title") %></div>
            <div class="my-bid-detail">
                <span class="my-bid-label">Bid Amount:</span>
                <span class="my-bid-value">$<%= bidRs.getDouble("bid_amount") %></span>
            </div>
            <div class="my-bid-detail">
                <span class="my-bid-label">Auction Ends:</span>
                <span class="my-bid-value"><%= bidRs.getTimestamp("end_date") %></span>
            </div>
        </div>
        <%
            }
            conn.close();
            if (!hasBids) {
        %>
        <div class="my-empty-msg">You have not placed any bids yet.</div>
        <%
            }
        %>
    </div>
</section>

<jsp:include page="footer.jspf" />
</body>
</html>