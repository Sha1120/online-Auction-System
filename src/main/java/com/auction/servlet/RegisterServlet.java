package com.auction.servlet;

import com.auction.ejb.UserService;
import com.auction.model.Status;
import com.auction.model.User;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userName = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            req.setAttribute("message", "Passwords do not match.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        String result = userService.registerUser(userName, email, password);

        if (result.equals("success")) {
            req.setAttribute("message", "Registration successful!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", result);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
