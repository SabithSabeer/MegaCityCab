package com.megacitycab.servlet;

import com.megacitycab.services.AdminAuthService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/adminlogin")
public class AdminLoginServlet extends HttpServlet {
    private final AdminAuthService authService = new AdminAuthService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            response.getWriter().write("Invalid input");
            return;
        }

        // Log incoming data
        System.out.println("Admin Login Attempt: " + email);

        // Check if admin credentials are valid
        if (authService.validateAdmin(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("admin_email", email);
            response.getWriter().write("success"); // Redirect on frontend
            System.out.println("Login successful for: " + email);
        } else {
            response.getWriter().write("Invalid email or password.");
            System.out.println("Login failed for: " + email);
        }
    }
}
