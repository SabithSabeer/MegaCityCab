package com.megacitycab.models;

import java.util.Date;

public class Booking {
    private String pickupLocation;
    private String destinationLocation;
    private String vehicleType;
    private double fare;
    private Date bookingDate;

    public Booking(String pickupLocation, String destinationLocation, String vehicleType, double fare) {
        this.pickupLocation = pickupLocation;
        this.destinationLocation = destinationLocation;
        this.vehicleType = vehicleType;
        this.fare = fare;
        this.bookingDate = new Date();
    }

    public String getPickupLocation() { return pickupLocation; }
    public String getDestinationLocation() { return destinationLocation; }
    public String getVehicleType() { return vehicleType; }
    public double getFare() { return fare; }
    public Date getBookingDate() { return bookingDate; }
}
