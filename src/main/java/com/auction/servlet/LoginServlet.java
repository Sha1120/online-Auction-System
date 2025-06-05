package com.auction.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/auctionDS");
        } catch (NamingException e) {
            throw new ServletException("Cannot retrieve java:comp/env/jdbc/auctionDS", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String input = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = dataSource.getConnection()) {
            // SQL to check either username or email
            String sql = "SELECT * FROM user WHERE (username = ? OR email = ?) AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, input);
                stmt.setString(2, input);
                stmt.setString(3, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // User found, create session
                        String userName = rs.getString("username");

                        HttpSession session = request.getSession();
                        session.setAttribute("username", userName);

                        response.sendRedirect("home.jsp");
                    } else {
                        // Invalid login
                        request.setAttribute("message", "Invalid username/email or password.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("DB error during login", e);
        }
    }
}
