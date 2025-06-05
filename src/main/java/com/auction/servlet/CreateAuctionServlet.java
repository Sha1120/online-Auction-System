package com.auction.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/CreateAuctionServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class CreateAuctionServlet extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/auctionDS");
        } catch (NamingException e) {
            throw new ServletException("Cannot retrieve java:comp/env/jdbc/auctionDS", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Session eke username ganna
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // 2. Form data ganima
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String startPriceStr = req.getParameter("startPrice");
        String endDateStr = req.getParameter("endDate");
        double startPrice = 0;
        try {
            startPrice = Double.parseDouble(startPriceStr);
        } catch (NumberFormatException e) {
            resp.getWriter().println("Invalid start price");
            return;
        }
        double currentPrice = startPrice;

        // 3. Image upload handling
        Part filePart = req.getPart("imageFile"); // name from the form input
        String fileName = getFileName(filePart);

        // Define upload path (adjust to your real path)
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filePath = uploadPath + File.separator + fileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, new File(filePath).toPath());
        }

        // Save relative path for DB (example: uploads/filename.jpg)
        String imgUrl = "uploads/" + fileName;

        // 4. Parse endDate to java.sql.Timestamp
        Timestamp endDateTimestamp = null;
        try {
            // incoming format: "yyyy-MM-ddTHH:mm" (datetime-local input)
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date parsedDate = format.parse(endDateStr);
            endDateTimestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            resp.getWriter().println("Invalid end date format");
            return;
        }

        try (Connection con = dataSource.getConnection()) {
            // 5. Get user ID from username
            int userId = -1;
            String sqlUser = "SELECT id FROM user WHERE username = ?";
            try (PreparedStatement psUser = con.prepareStatement(sqlUser)) {
                psUser.setString(1, username);
                try (ResultSet rs = psUser.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");
                    } else {
                        resp.getWriter().println("User not found");
                        return;
                    }
                }
            }

            // 6. Insert auction
            String sqlInsert = "INSERT INTO actions (title, description, img_url, start_price, current_price, create_date, end_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                psInsert.setString(1, title);
                psInsert.setString(2, description);
                psInsert.setString(3, imgUrl);
                psInsert.setDouble(4, startPrice);
                psInsert.setDouble(5, currentPrice);
                psInsert.setTimestamp(6, new Timestamp(new Date().getTime()));
                psInsert.setTimestamp(7, endDateTimestamp);
                psInsert.setInt(8, userId);

                psInsert.executeUpdate();
            }

            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>");
            resp.getWriter().println("alert('Auction created successfully!');");
            resp.getWriter().println("window.location.href = 'create-action.jsp';");
            resp.getWriter().println("</script>");


        } catch (SQLException e) {
            throw new ServletException("DB error: " + e.getMessage(), e);
        }
    }

    // Helper method to extract filename from Part header
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp == null) return null;
        for (String cd : contentDisp.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return new File(fileName).getName();
            }
        }
        return null;
    }
}
