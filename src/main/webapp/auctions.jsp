<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Live Auctions - Distributed Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<section class="hero">
    <h2>Live & Upcoming Auctions</h2>
    <p>Bid in real-time and win exclusive items!</p>
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
</section>

<section class="auction-list">
    <c:forEach var="auction" items="${auctions}">
        <div class="auction-card">
            <img src="${pageContext.request.contextPath}/${auction.imgUrl}" class="auction-img" alt="Auction Image" />
            <h3>${auction.title}</h3>
            <p><strong>Current Bid:</strong> <span style="color:green;">$${auction.currentPrice}</span></p>

                <%-- Calculate time remaining in JavaScript or backend better; simple example below --%>
            <p>Auction ends on: <span style="color:darkred;">${auction.endDate}</span></p>

            <a href="${pageContext.request.contextPath}/auction-detail?auctionId=${auction.id}" class="cta-btn">View & Bid</a>
        </div>
    </c:forEach>

</section>
<jsp:include page="footer.jspf" />
</body>
</html>