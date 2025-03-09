package com.megacitycab.servlet;

import com.megacitycab.models.Driver;  // Ensure you are using the correct Driver class
import com.megacitycab.services.ManageDriverService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ManageDriverServlet extends HttpServlet {

    private ManageDriverService driverService;

    @Override
    public void init() throws ServletException {
        // Initialize the service class (which will be responsible for DB interactions)
        driverService = new ManageDriverService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Driver> drivers = driverService.getAllDrivers();  // Change to Driver class
            // Convert list of drivers to JSON using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(drivers);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle driver creation or update here (we will implement in service class)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read JSON data from the request
        ObjectMapper objectMapper = new ObjectMapper();
        Driver driver = objectMapper.readValue(request.getReader(), Driver.class);  // Change to Driver class

        try {
            if (driver.getDriverId() == 0) {
                driverService.addDriver(driver); // Add a new driver
            } else {
                driverService.updateDriver(driver); // Update an existing driver
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String driverId = request.getParameter("driver_id");

        try {
            driverService.deleteDriver(Integer.parseInt(driverId)); // Delete driver by ID
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
