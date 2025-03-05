package com.megacitycab.dao;

import com.megacitycab.models.Booking;
import com.megacitycab.config.DBConn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDAO {
    public static boolean saveBooking(Booking booking) {
        String query = "INSERT INTO bookings (pickup_location, destination_location, vehicle_type, fare, booking_time) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, booking.getPickupLocation());
            stmt.setString(2, booking.getDestinationLocation());
            stmt.setString(3, booking.getVehicleType());
            stmt.setDouble(4, booking.getFare());
            stmt.setTimestamp(5, new java.sql.Timestamp(booking.getBookingDate().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
