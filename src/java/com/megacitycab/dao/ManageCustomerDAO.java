package com.megacitycab.dao;

import com.megacitycab.config.DBConn;
import com.megacitycab.models.ManageCustomer;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageCustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(ManageCustomerDAO.class.getName());

    // Get all customers
    public List<ManageCustomer> getAllCustomers() {
        List<ManageCustomer> customers = new ArrayList<>();
        String query = "SELECT customer_id, username, email, phone, address, nic FROM customer";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new ManageCustomer(
                    rs.getInt("customer_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("nic")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching customers", e);
        }
        return customers;
    }

    // Insert a new customer
    public boolean insertCustomer(ManageCustomer customer) {
        String query = "INSERT INTO customer (username, email, password, phone, address, nic) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, hashPassword(customer.getPassword()));  // Hash password before inserting
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting customer", e);
            return false;
        }
    }

    // Delete a customer by ID
    public boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            return false;
        }
    }

    // Update customer details
    public boolean updateCustomer(ManageCustomer customer) {
        StringBuilder query = new StringBuilder("UPDATE customer SET username = ?, email = ?, phone = ?, address = ?, nic = ?");
        boolean updatePassword = (customer.getPassword() != null && !customer.getPassword().isEmpty());

        if (updatePassword) {
            query.append(", password = ?");
        }
        query.append(" WHERE customer_id = ?");

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getNic());

            int paramIndex = 6;
            if (updatePassword) {
                stmt.setString(paramIndex++, hashPassword(customer.getPassword()));
            }
            stmt.setInt(paramIndex, customer.getCustomerId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            return false;
        }
    }

    // Check if email already exists
    public boolean isEmailExists(String email) {
        String query = "SELECT 1 FROM customer WHERE email = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if email exists
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking email existence", e);
            return false;
        }
    }

    // Helper method to hash password securely
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // Increased salt rounds for better security
    }
}
