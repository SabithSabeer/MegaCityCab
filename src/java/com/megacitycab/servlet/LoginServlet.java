package com.megacitycab.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.megacitycab.config.DBConn;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountType = request.getParameter("accountType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Account Type: " + accountType);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Initialize JDBC connection using DBConn class
        try (Connection conn = DBConn.getConnection()) {
            String query = "";

            // Select query based on account type (driver or customer)
            if ("driver".equals(accountType)) {
                query = "SELECT * FROM driver WHERE LOWER(email) = LOWER(?) AND password = ?";
            } else if ("customer".equals(accountType)) {
                query = "SELECT * FROM customer WHERE LOWER(email) = LOWER(?) AND password = ?";
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Account found, redirect to respective dashboard
                        if ("driver".equals(accountType)) {
                            response.sendRedirect("driver.html");
                        } else {
                            response.sendRedirect("home.html");
                        }
                    } else {
                        // Account not found, display error message
                        System.out.println("Account not found in the database.");
                        request.setAttribute("errorMessage", "No account found with the provided email and password.");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
                        dispatcher.forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
            dispatcher.forward(request, response);
        }
    }
}

