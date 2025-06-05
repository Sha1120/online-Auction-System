<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Auction - Distributed Online Auction System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<section class="hero">
    <h2>Create New Auction</h2>
    <p>Launch an item for live bidding. Fill out the auction details below.</p>
</section>
<div class="form-container">
    <h3><i class="fas fa-gavel"></i> Auction Details</h3>
    <form action="CreateAuctionServlet" method="post" enctype="multipart/form-data">
    <label for="title">Item Title</label>
        <input type="text" id="title" name="title" required />

        <label for="desc">Description</label>
        <textarea id="desc" name="description" required></textarea>

        <label for="startPrice">Start Price ($)</label>
        <input type="number" id="startPrice" name="startPrice" min="1" required />

        <label for="endDate">End Date & Time</label>
        <input type="datetime-local" id="endDate" name="endDate" required>

        <label for="image">Upload Image</label>
        <input type="file" id="image" name="imageFile" accept="image/*" required />



        <button type="submit" class="cta-btn"><i class="fas fa-check"></i> Create Auction</button>
    </form>
</div>
<%@ include file="footer.jspf" %>
</body>
</html>