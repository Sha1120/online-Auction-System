package com.auction.servlet;

import com.auction.model.User;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Resource(name = "jdbc/auctionDS")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/auctionDS");
        } catch (NamingException e) {
            throw new ServletException("Cannot retrieve java:comp/env/jdbc/auctionDS", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match!");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        }

        try (Connection conn = dataSource.getConnection()) {

            // Set default status_id to 1 (you can change based on your DB)
            int defaultStatusId = 1;

            String sql = "INSERT INTO user (username, email, password, joing_date, status_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // You can hash this for security
            stmt.setTimestamp(4, new Timestamp(new Date().getTime()));
            stmt.setInt(5, defaultStatusId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                request.setAttribute("message", "Registration successful. Please log in.");
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("message", "Registration failed. Try again.");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        }
    }
}
