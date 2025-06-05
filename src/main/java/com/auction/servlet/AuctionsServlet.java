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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/auctions")
public class AuctionsServlet extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/auctionDS");
        } catch (NamingException e) {
            throw new ServletException("Cannot find datasource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Actions> auctions = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT id, title, description, img_url, start_price, current_price, create_date, end_date FROM actions";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Actions auction = new Actions();
                        auction.setId(rs.getInt("id"));
                        auction.setTitle(rs.getString("title"));
                        auction.setDescription(rs.getString("description"));
                        auction.setImgUrl(rs.getString("img_url"));
                        auction.setStartPrice(rs.getDouble("start_price"));
                        auction.setCurrentPrice(rs.getDouble("current_price"));
                        auction.setCreateDate(rs.getTimestamp("create_date"));
                        auction.setEndDate(rs.getTimestamp("end_date"));
                        auctions.add(auction);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB error", e);
        }

        // Pass the auctions list to JSP
        req.setAttribute("auctions", auctions);

        // Forward request to JSP page
        req.getRequestDispatcher("/auctions.jsp").forward(req, resp);
    }
}
