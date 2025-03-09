package com.megacitycab.services;

import com.megacitycab.models.Driver;  // Use the Driver model class
import com.megacitycab.config.DBConn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageDriverService {

    private final DBConn dbConn;

    // Constructor to initialize DBConn instance
    public ManageDriverService() {
        this.dbConn = new DBConn(); // Initialize the DBConn class
    }

    // Method to get all drivers
    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM driver"; // Assuming the table is 'driver'
        
        try (Connection connection = dbConn.getConnection();  // Use the DBConn instance to get connection
             Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                Driver driver = new Driver(
                    rs.getInt("driver_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("vehicle_type"),
                    rs.getString("vehicle_no"),
                    rs.getString("status")
                );
                drivers.add(driver);
            }
        }
        return drivers;
    }

// Method to add a new driver
public void addDriver(Driver driver) throws SQLException {
    String query = "INSERT INTO driver (first_name, last_name, email, password, phone, vehicle_type, vehicle_no, status) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection connection = dbConn.getConnection(); // Use the DBConn instance to get connection
         PreparedStatement stmt = connection.prepareStatement(query)) {
         
        stmt.setString(1, driver.getFirstName());
        stmt.setString(2, driver.getLastName());
        stmt.setString(3, driver.getEmail());

        // Check if 'password' field exists in the Driver model before setting it
        if (driver.getPassword() != null) {
            stmt.setString(4, (String) driver.getPassword());  // Assuming password field exists
        } else {
            stmt.setNull(4, Types.VARCHAR);  // Set null if password is not provided
        }

        stmt.setString(5, driver.getPhone());
        stmt.setString(6, driver.getVehicleType());
        stmt.setString(7, driver.getVehicleNo());
        stmt.setString(8, driver.getStatus());
        stmt.executeUpdate();
    }
}

// Method to update driver details
public void updateDriver(Driver driver) throws SQLException {
    String query = "UPDATE driver SET first_name = ?, last_name = ?, email = ?, password = ?, phone = ?, " +
                   "vehicle_type = ?, vehicle_no = ?, status = ? WHERE driver_id = ?";
    
    try (Connection connection = dbConn.getConnection(); // Use the DBConn instance to get connection
         PreparedStatement stmt = connection.prepareStatement(query)) {
         
        stmt.setString(1, driver.getFirstName());
        stmt.setString(2, driver.getLastName());
        stmt.setString(3, driver.getEmail());

        // Check if 'password' field exists in the Driver model before setting it
        if (driver.getPassword() != null) {
            stmt.setString(4, (String) driver.getPassword());  // Assuming password field exists
        } else {
            stmt.setNull(4, Types.VARCHAR);  // Set null if password is not provided
        }

        stmt.setString(5, driver.getPhone());
        stmt.setString(6, driver.getVehicleType());
        stmt.setString(7, driver.getVehicleNo());
        stmt.setString(8, driver.getStatus());
        stmt.setInt(9, driver.getDriverId());
        stmt.executeUpdate();
    }
}


    // Method to delete a driver
    public void deleteDriver(int driverId) throws SQLException {
        String query = "DELETE FROM driver WHERE driver_id = ?";
        
        try (Connection connection = dbConn.getConnection();  // Use the DBConn instance to get connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setInt(1, driverId);
            stmt.executeUpdate();
        }
    }
}
