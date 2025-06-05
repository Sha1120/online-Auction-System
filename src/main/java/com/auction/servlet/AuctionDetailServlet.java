package com.auction.servlet;

import com.auction.model.Actions;
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
;

@WebServlet("/auction-detail")
public class AuctionDetailServlet extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/auctionDS");
        } catch (NamingException e) {
            throw new ServletException("Cannot access datasource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String auctionIdStr = req.getParameter("auctionId");

        if (auctionIdStr == null || auctionIdStr.isEmpty()) {
            resp.sendRedirect("auctions.jsp");
            return;
        }

        int auctionId = Integer.parseInt(auctionIdStr);
        Actions auction = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM actions WHERE id = ?")) {

            ps.setInt(1, auctionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                auction = new Actions();
                auction.setId(rs.getInt("id"));
                auction.setTitle(rs.getString("title"));
                auction.setDescription(rs.getString("description"));
                auction.setImgUrl(rs.getString("img_url"));
                auction.setStartPrice(rs.getDouble("start_price"));
                auction.setCurrentPrice(rs.getDouble("current_price"));
                auction.setCreateDate(rs.getTimestamp("create_date"));
                auction.setEndDate(rs.getTimestamp("end_date"));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (auction != null) {
            req.setAttribute("auction", auction);
            req.getRequestDispatcher("auction-detail.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("auctions.jsp"); // auction not found
        }
    }
}
