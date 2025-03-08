package com.megacitycab.models;

public class VehicleType {
    private int vehicleId;
    private String vehicleName;
    private int vehicleCapacity;
    private double vehicleRate;

    public VehicleType(int vehicleId, String vehicleName, int vehicleCapacity, double vehicleRate) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleRate = vehicleRate;
    }

    public int getVehicleId() { return vehicleId; }
    public String getVehicleName() { return vehicleName; }
    public int getVehicleCapacity() { return vehicleCapacity; }
    public double getVehicleRate() { return vehicleRate; }
}
