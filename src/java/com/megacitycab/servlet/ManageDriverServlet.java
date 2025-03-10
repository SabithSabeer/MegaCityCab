package com.megacitycab.servlet;

import com.google.gson.Gson;
import com.megacitycab.dao.DriverDAO;
import com.megacitycab.models.Driver;
import com.megacitycab.models.VehicleType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

@WebServlet("/ManageDriverServlet")
public class ManageDriverServlet extends HttpServlet {
    private final DriverDAO driverDAO = new DriverDAO();
    private final Gson gson = new Gson();

    // Handle GET request (Fetch drivers)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Driver> drivers = driverDAO.getAllDrivers();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(drivers));
    }

    // Handle POST request (Add driver)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Driver driver = gson.fromJson(request.getReader(), Driver.class);
        boolean success = driverDAO.addDriver(driver);
        response.setStatus(success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
    }

    // Handle PUT request (Update driver)
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Driver driver = gson.fromJson(request.getReader(), Driver.class);
        boolean success = driverDAO.updateDriver(driver);
        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    // Handle DELETE request (Delete driver)
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int driverId = Integer.parseInt(request.getParameter("driver_id"));
        boolean success = driverDAO.deleteDriver(driverId);
        response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    // Handle GET request to fetch vehicle types for the dropdown
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<VehicleType> vehicleTypes = driverDAO.getAllVehicleTypes();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(vehicleTypes));
    }
}
