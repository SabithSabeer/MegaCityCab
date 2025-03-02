package com.megacitycab.servlet;

import com.megacitycab.config.DBConn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String category = request.getParameter("category"); // Get selected category (customer/driver)

        // Establish DB connection
        try (Connection conn = DBConn.getConnection()) {

            if (conn == null) {
                response.sendRedirect("login.html?error=Database connection failed");
                return;
            }

            String table = category; // Use selected category (either "customer" or "driver")
            String query = "SELECT username, password FROM " + table + " WHERE username = ? AND password = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // If user is found, set session and redirect
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                    if (category.equals("customer")) {
                        session.setAttribute("loginMessage", "Login successful! Welcome, customer.");
                        response.sendRedirect("home.html"); // Redirect to customer home page
                    } else if (category.equals("driver")) {
                        session.setAttribute("loginMessage", "Login successful! Welcome, driver.");
                        response.sendRedirect("driver.html"); // Redirect to driver home page
                    }
                } else {
                    // If no match is found, redirect with error message
                    response.sendRedirect("login.html?error=Invalid username or password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=Something went wrong");
        }
    }
}
