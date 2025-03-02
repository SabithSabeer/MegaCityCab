package com.megacitycab.servlet;
import com.megacitycab.dao.CustomerDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");

        // Use DAO to handle the database logic
        CustomerDAO customerDAO = new CustomerDAO();

        // Check if user exists
        if (customerDAO.isUserExists(username, email, nic)) {
            // Send error message if user exists
            request.setAttribute("errorMessage", "Username, email, or NIC already exists!");
            request.getRequestDispatcher("signup.html").forward(request, response);
            return;
        }

        // Register new user
        boolean success = customerDAO.registerCustomer(username, email, password, phone, address, nic);

        if (success) {
            // On successful signup, redirect to login page with a success message
            request.setAttribute("successMessage", "Registration successful! Please log in.");
            request.getRequestDispatcher("login.html").forward(request, response);
        } else {
            // If registration failed, show an error message
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("signup.html").forward(request, response);
        }
    }
}