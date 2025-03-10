package com.megacitycab.dao;

import com.megacitycab.config.DBConn;
import com.megacitycab.models.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    
    // Get all admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT admin_id, username, email, phone, nic FROM admin";  // Ensure admin_id is selected

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("nic"),
                    null // Password is not retrieved for security
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("Error loading admins: " + e.getMessage());
        }
        return admins;
    }

    // Add admin with bcrypt password hashing
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admin (username, email, phone, nic, password) VALUES (?, ?, ?, ?, ?)";
        
        // Hash the password before saving to database
        String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPhone());
            stmt.setString(4, admin.getNic());
            stmt.setString(5, hashedPassword);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    admin.setId(generatedKeys.getInt(1)); // Set new ID
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
        }
        return false;
    }

    // Update an admin by username
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admin SET username=?, email=?, phone=?, nic=? WHERE username=?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPhone());
            stmt.setString(4, admin.getNic());
            stmt.setString(5, admin.getUsername()); // Update based on username

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
        }
        return false;
    }

    // Delete an admin by username
    public boolean deleteAdmin(String username) {
        String sql = "DELETE FROM admin WHERE username=?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username); // Delete based on username
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
        }
        return false;
    }
}
