package com.megacitycab.models;

public class VehicleType {
    private int vehicleId;
    private String vehicleName;
    private int vehicleCapacity;
    private double vehicleRate;

    // Constructor with parameters
    public VehicleType(int vehicleId, String vehicleName, int vehicleCapacity, double vehicleRate) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleRate = vehicleRate;
    }

    // Default constructor
    public VehicleType() {
        // Empty constructor for JSON deserialization or other purposes
    }

    // Getters
    public int getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public double getVehicleRate() {
        return vehicleRate;
    }

    // Setters
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public void setVehicleCapacity(int vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public void setVehicleRate(double vehicleRate) {
        this.vehicleRate = vehicleRate;
    }
}
