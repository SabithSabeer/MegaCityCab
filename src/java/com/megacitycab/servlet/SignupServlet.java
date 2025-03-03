package com.megacitycab.servlet;


import com.megacitycab.config.DBConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");
        
        if (!isValidEmail(email) || !isValidPassword(password)) {
            response.sendRedirect("signup.html?error=Invalid email or password");
            return;
        }
        
        String hashedPassword = hashPassword(password);
        
        try (Connection conn = DBConn.getConnection()) {
            if (isUserExists(conn, email, nic)) {
                response.sendRedirect("signup.html?error=User already exists");
                return;
            }
            
            if (saveUser(conn, username, email, hashedPassword, phone, address, nic)) {
                response.sendRedirect("login.html?success=Signup successful");
            } else {
                response.sendRedirect("signup.html?error=Signup failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signup.html?error=Database error");
        }
    }

    private boolean isUserExists(Connection conn, String email, String nic) throws SQLException {
        String query = "SELECT * FROM customer WHERE email = ? OR nic = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, nic);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean saveUser(Connection conn, String username, String email, String password, String phone, String address, String nic) throws SQLException {
        String query = "INSERT INTO customer (username, email, password, phone, address, nic) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, nic);
            return stmt.executeUpdate() > 0;
        }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
