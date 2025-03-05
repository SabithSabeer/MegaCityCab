package com.megacitycab.servlet;

import com.megacitycab.models.Booking;
import com.megacitycab.services.FareCalculatorService;
import com.megacitycab.dao.BookingDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookCab")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pickupLocation = request.getParameter("pickupLocation");
        String destinationLocation = request.getParameter("destinationLocation");
        String vehicleType = request.getParameter("vehicleType");
        double distance = 10; // You can integrate Google Maps API for real distance calculation

        double fare = FareCalculatorService.calculateFare(vehicleType, distance);
        Booking booking = new Booking(pickupLocation, destinationLocation, vehicleType, fare);

        if (BookingDAO.saveBooking(booking)) {
            response.getWriter().write("Booking confirmed! Total Fare: $" + fare);
        } else {
            response.getWriter().write("Booking failed. Try again.");
        }
    }
}
