<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auction Detail - Distributed Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body id="auction-detail-body">
<jsp:include page="header.jsp" />

<%-- Set loggedUser as JSTL variable --%>
<c:set var="loggedUser" value="${sessionScope.loggedUser}" />

<section class="auction-detail-hero">
    <h2 id="auction-detail-title">${auction.title}</h2>
    <p>Auction ends in: <span style="color:darkred;">${auction.timeRemaining}</span></p>
</section>

<div class="auction-detail" style="display: flex; gap: 20px;" id="auction-detail-section">
    <img src="${pageContext.request.contextPath}/${auction.imgUrl}" id="auction-detail-image" alt="${auction.title}">

    <div id="auction-detail-info">
        <h3>Description</h3>
        <p>${auction.description}</p>
        <h3 id="auction-detail-current-bid" style="color: #ea0808">Current Bid: $${auction.currentPrice}</h3>

        <p><strong>Auction Ends In:</strong> ${auction.timeRemaining}</p>

        <form action="PlaceBidServlet" method="post" id="auction-detail-bid-form">
            <input type="hidden" name="auctionId" value="${auction.id}" />

            <input type="number"
                   name="bidAmount"
                   min="${auction.currentPrice + 1}"
                   required
                   placeholder="Your Bid"
                   <c:if test="${auction.timeRemaining == 'Auction ended'}">disabled</c:if> />

            <c:choose>
                <c:when test="${auction.timeRemaining == 'Auction ended'}">
                    <button type="submit" class="cta-btn" style="background-color:red;" disabled>
                        Auction Ended
                    </button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="cta-btn">
                        Place Bid
                    </button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</div>

<%@ include file="footer.jspf" %>
</body>
</html>
