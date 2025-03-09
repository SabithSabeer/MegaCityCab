package com.megacitycab.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.megacitycab.config.DBConn;

// Model class for VehicleType
class VehicleType {
    private int vehicleId;
    private String vehicleType;
    private double baseFare;
    private double perKmFare;

    public VehicleType(int vehicleId, String vehicleType, double baseFare, double perKmFare) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.baseFare = baseFare;
        this.perKmFare = perKmFare;
    }

    public int getVehicleId() { return vehicleId; }
    public String getVehicleType() { return vehicleType; }
    public double getBaseFare() { return baseFare; }
    public double getPerKmFare() { return perKmFare; }
}

@WebServlet("/ManageVehicleServlet")
public class ManageVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Fetch Vehicle Data
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<VehicleType> vehicleList = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT vehicle_id, vehicle_type, base_fare, per_km_fare FROM vehicle_types");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicleList.add(new VehicleType(
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("base_fare"),
                        rs.getDouble("per_km_fare")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Database error while fetching vehicle data.\"}");
            return;
        }

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(vehicleList));
    }

    // Add New Vehicle Type
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(request.getReader(), JsonObject.class);

        String vehicleType = jsonObject.get("vehicleType").getAsString();
        double baseFare = jsonObject.get("baseFare").getAsDouble();
        double perKmFare = jsonObject.get("perKmFare").getAsDouble();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO vehicle_types (vehicle_type, base_fare, per_km_fare) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vehicleType);
            stmt.setDouble(2, baseFare);
            stmt.setDouble(3, perKmFare);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\": \"Vehicle type added successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Failed to add vehicle type.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Database error while adding vehicle type.\"}");
        }
    }

    // Update Vehicle Type
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject jsonObject = new Gson().fromJson(request.getReader(), JsonObject.class);

        int vehicleId = jsonObject.get("vehicleId").getAsInt();
        String vehicleType = jsonObject.get("vehicleType").getAsString();
        double baseFare = jsonObject.get("baseFare").getAsDouble();
        double perKmFare = jsonObject.get("perKmFare").getAsDouble();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE vehicle_types SET vehicle_type = ?, base_fare = ?, per_km_fare = ? WHERE vehicle_id = ?")) {

            stmt.setString(1, vehicleType);
            stmt.setDouble(2, baseFare);
            stmt.setDouble(3, perKmFare);
            stmt.setInt(4, vehicleId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Vehicle type updated successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Vehicle type not found for update.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Database error while updating vehicle type.\"}");
        }
    }

    // Delete Vehicle Type
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM vehicle_types WHERE vehicle_id = ?")) {

            stmt.setInt(1, vehicleId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Vehicle type deleted successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Vehicle type not found for deletion.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Database error while deleting vehicle type.\"}");
        }
    }
}
