package com.megacitycab.dao;

import com.megacitycab.config.DBConn;
import com.megacitycab.models.Driver;
import com.megacitycab.models.VehicleType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class DriverDAO {

    // Add a new driver to the database
    public boolean addDriver(Driver driver) {
        String hashedPassword = BCrypt.hashpw(driver.getPassword(), BCrypt.gensalt());
        String query = "INSERT INTO driver (first_name, last_name, email, password, phone, vehicle_type, vehicle_no, status, profile_picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getEmail());
            stmt.setString(4, hashedPassword); // Store the hashed password
            stmt.setString(5, driver.getPhone());
            stmt.setString(6, driver.getVehicleType());
            stmt.setString(7, driver.getVehicleNo());
            stmt.setString(8, driver.getStatus());
            stmt.setString(9, driver.getProfilePicture());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update driver details (without updating password)
    public boolean updateDriver(Driver driver) {
        String query = "UPDATE driver SET first_name=?, last_name=?, email=?, phone=?, vehicle_type=?, vehicle_no=?, status=?, profile_picture=? WHERE driver_id=?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getEmail());
            stmt.setString(4, driver.getPhone());
            stmt.setString(5, driver.getVehicleType());
            stmt.setString(6, driver.getVehicleNo());
            stmt.setString(7, driver.getStatus());
            stmt.setString(8, driver.getProfilePicture());
            stmt.setInt(9, driver.getDriverId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a driver
    public boolean deleteDriver(int driverId) {
        String query = "DELETE FROM driver WHERE driver_id=?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all drivers
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM driver";
        try (Connection conn = DBConn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setDriverId(rs.getInt("driver_id"));
                driver.setFirstName(rs.getString("first_name"));
                driver.setLastName(rs.getString("last_name"));
                driver.setEmail(rs.getString("email"));
                driver.setPhone(rs.getString("phone"));
                driver.setVehicleType(rs.getString("vehicle_type"));
                driver.setVehicleNo(rs.getString("vehicle_no"));
                driver.setStatus(rs.getString("status"));
                driver.setProfilePicture(rs.getString("profile_picture"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    // Fetch all vehicle types for the dropdown
    public List<VehicleType> getAllVehicleTypes() {
        List<VehicleType> vehicleTypes = new ArrayList<>();
        String query = "SELECT * FROM vehicle_type";
        try (Connection conn = DBConn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleId(rs.getInt("vehicle_id"));
                vehicleType.setVehicleName(rs.getString("vehicle_name"));
                vehicleType.setVehicleCapacity(rs.getInt("vehicle_capacity"));
                vehicleType.setVehicleRate(rs.getDouble("vehicle_rate"));
                vehicleTypes.add(vehicleType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleTypes;
    }
}
