package com.megacitycab.dao;

import com.megacitycab.config.DBConn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {
        this.connection = DBConn.getConnection();
    }

   public boolean registerCustomer(String username, String email, String password, String phone, String address, String nic) {
    String query = "INSERT INTO customer (username, email, password, phone, address, nic) VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setString(2, email);
        stmt.setString(3, password);  // Consider hashing the password
        stmt.setString(4, phone);
        stmt.setString(5, address);
        stmt.setString(6, nic);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean isUserExists(String username, String email, String nic) {
    String query = "SELECT customer_id FROM customer WHERE username = ? OR email = ? OR nic = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setString(2, email);
        stmt.setString(3, nic);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Returns true if user exists
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
