package com.megacitycab.dao;

import com.megacitycab.models.Customer;
import com.megacitycab.models.ManageCustomer;
import java.sql.*;
import java.util.List;

public class CustomerDAO {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=MegaCityCab;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "sql";

    public boolean isEmailExists(String email) throws SQLException {
        String query = "SELECT email FROM customer WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean registerCustomer(Customer customer) {
        String query = "INSERT INTO customer (username, email, password, phone, address, nic) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPassword()); // Hashed password
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ManageCustomer> getAllCustomers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
