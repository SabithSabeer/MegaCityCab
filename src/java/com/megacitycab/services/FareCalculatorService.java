package com.megacitycab.services;

import com.megacitycab.models.Sedan;
import com.megacitycab.models.SUV;
import com.megacitycab.models.Vehicle;

public class FareCalculatorService {
    public static double calculateFare(String vehicleType, double distance) {
        Vehicle vehicle;
        switch (vehicleType.toLowerCase()) {
            case "suv":
                vehicle = new SUV();
                break;
            case "sedan":
            default:
                vehicle = new Sedan();
                break;
        }
        return vehicle.calculateFare(distance);
    }
}
