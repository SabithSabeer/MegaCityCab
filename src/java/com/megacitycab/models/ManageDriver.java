package com.megacitycab.models;

import com.megacitycab.config.DBConn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageDriver {

    private DBConn dbConn; // Reference to DBConn class for database interaction

    public ManageDriver() {
        this.dbConn = new DBConn(); // Initialize the DBConn class
    }

    // Method to get all drivers from the database
    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";  // Assuming your driver table is named 'drivers'

        try (Connection connection = dbConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Driver driver = new Driver(
                        resultSet.getInt("driver_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("vehicle_type"),
                        resultSet.getString("vehicle_no"),
                        resultSet.getString("status"));
                drivers.add(driver);
            }
        }
        return drivers;
    }

    // Method to add or update a driver
    public boolean saveDriver(Driver driver) throws SQLException {
        String query;
        boolean isUpdate = driver.getDriverId() > 0;

        if (isUpdate) {
            query = "UPDATE drivers SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
                    "vehicle_type = ?, vehicle_no = ?, status = ? WHERE driver_id = ?";
        } else {
            query = "INSERT INTO drivers (first_name, last_name, email, phone, vehicle_type, vehicle_no, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        try (Connection connection = dbConn.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, driver.getFirstName());
            statement.setString(2, driver.getLastName());
            statement.setString(3, driver.getEmail());
            statement.setString(4, driver.getPhone());
            statement.setString(5, driver.getVehicleType());
            statement.setString(6, driver.getVehicleNo());
            statement.setString(7, driver.getStatus());

            if (isUpdate) {
                statement.setInt(8, driver.getDriverId());
            }

            return statement.executeUpdate() > 0;
        }
    }

    // Method to delete a driver by ID
    public boolean deleteDriver(int driverId) throws SQLException {
        String query = "DELETE FROM drivers WHERE driver_id = ?";

        try (Connection connection = dbConn.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, driverId);
            return statement.executeUpdate() > 0;
        }
    }

    // Method to fetch vehicle types (if needed for dynamic dropdown)
    public List<String> getVehicleTypes() throws SQLException {
        List<String> vehicleTypes = new ArrayList<>();
        String query = "SELECT DISTINCT vehicle_type FROM drivers";  // Assuming your 'drivers' table has a 'vehicle_type' field

        try (Connection connection = dbConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                vehicleTypes.add(resultSet.getString("vehicle_type"));
            }
        }
        return vehicleTypes;
    }
}
