<link rel="stylesheet" type="text/css" href="css/style.css" />

<header id="header-body">

    <!-- Place this inside your <body> of auction-bid-details.jsp or relevant JSP/HTML file -->
    <div id="toast-container" style="position: fixed; top: 30px; right: 30px; z-index: 9999;"></div>

    <div id="header-container">

        <!-- Logo -->
        <a href="index.jsp" id="header-logo-link">
            <img src="uploads/icon/auction-logo.png" alt="Auction Logo" id="header-logo-img" onerror="this.style.display='none'">
            <span id="header-title">Auction Bid System</span>
        </a>

        <!-- Navigation -->
        <nav id="header-nav">
            <a href="home.jsp" class="header-nav-link">Home</a>
            <a href="auctions" class="header-nav-link">Browse Auctions</a>
            <a href="create-action.jsp" class="header-nav-link">Create Auction</a>
            <a href="mybids.jsp" class="header-nav-link">My Bids</a>
            <%
                String loggedUser = (String) session.getAttribute("username");
                if (loggedUser != null) {
            %>
            <div class="header-user-dropdown">
                <span id="header-username">
                      <%= loggedUser %>
                          <i class="fa fa-caret-up"></i>
                </span>
                <div class="dropdown-content">
                    <a href="LogoutServlet">Logout</a>
                    <!-- You can add more links here if needed -->
                </div>
            </div>
            <%
            } else {
            %>
            <a href="login.jsp" class="header-auth-link">Login</a>
            <%
                }
            %>

        </nav>

    </div>
    <script src="js/auction-bid-updates.js"></script>
</header>
