package com.auction.servlet;

import com.auction.jms.BidNotifier;
import com.auction.model.Actions;
import com.auction.model.Bids;
import com.auction.model.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/PlaceBidServlet")
public class PlaceBidServlet extends HttpServlet {

    @Inject
    private BidNotifier bidNotifier;

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/auctionDS");
        } catch (NamingException e) {
            throw new ServletException("DB Connection setup failed!", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));

        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = null;

        try (Connection conn = dataSource.getConnection()) {
            // Fetch user
            String userSql = "SELECT id, username FROM user WHERE username = ?";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                user = new User();
                user.setId(userRs.getInt("id"));
                user.setUserName(userRs.getString("username"));
                request.getSession().setAttribute("loggedUser", user);
            } else {
                response.sendRedirect("login.jsp");
                return;
            }

            conn.setAutoCommit(false);

            // Get currentPrice and endDate
            String selectSql = "SELECT current_price, end_date, title, description,img_url FROM actions WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, auctionId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                double currentPrice = rs.getDouble("current_price");
                Timestamp endDate = rs.getTimestamp("end_date");

                if (endDate.before(new Timestamp(System.currentTimeMillis()))) {
                    conn.rollback();
                    response.sendRedirect("auction-detail.jsp?id=" + auctionId + "&error=AuctionEnded");
                    return;
                }

                if (bidAmount <= currentPrice) {
                    conn.rollback();
                    response.sendRedirect("auction-detail.jsp?id=" + auctionId + "&error=LowBid");
                    return;
                }

                // Insert new bid
                String insertSql = "INSERT INTO bids (bid_amount, bid_time, user_id,actions_id) VALUES (?, NOW(), ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setDouble(1, bidAmount);
                insertStmt.setInt(2, user.getId());
                insertStmt.setInt(3, auctionId);
                insertStmt.executeUpdate();

                // Update current price
                String updateSql = "UPDATE actions SET current_price = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setDouble(1, bidAmount);
                updateStmt.setInt(2, auctionId);
                updateStmt.executeUpdate();

                conn.commit();

                // Now fetch updated auction details and bids to send to JSP
                Actions auction = new Actions();
                auction.setId(auctionId);
                auction.setCurrentPrice(bidAmount);
                auction.setEndDate(endDate);
                auction.setTitle(rs.getString("title"));
                auction.setDescription(rs.getString("description"));
                auction.setImgUrl(rs.getString("img_url"));

                String Title = auction.getTitle();

                // Fetch bids for this auction
                String bidsSql = "SELECT b.bid_amount, b.bid_time, u.username FROM bids b JOIN user u ON b.user_id = u.id WHERE b.actions_id = ? ORDER BY b.bid_time DESC";
                PreparedStatement bidsStmt = conn.prepareStatement(bidsSql);
                bidsStmt.setInt(1, auctionId);
                ResultSet bidsRs = bidsStmt.executeQuery();

                List<Bids> bids = new ArrayList<>();
                while (bidsRs.next()) {
                    Bids bid = new Bids();
                    bid.setBidAmount(bidsRs.getDouble("bid_amount"));
                    bid.setBidTime(bidsRs.getTimestamp("bid_time"));
                    bid.setUser(bidsRs.getString("username"));
                    bids.add(bid);
                }

                // Set attributes for JSP
                request.setAttribute("auction", auction);
                request.setAttribute("bids", bids);

                // After a new bid is successfully placed and auction/bid details are updated:
                bidNotifier.notifyNewBid(Title, bidAmount);

                // Forward to JSP (not redirect)
                request.getRequestDispatcher("auction-detail.jsp").forward(request, response);


            } else {
                conn.rollback();
                response.sendRedirect("auction-detail.jsp?id=" + auctionId + "&error=AuctionNotFound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("auction-detail.jsp?id=" + auctionId + "&error=ServerError");
        }
    }

}
