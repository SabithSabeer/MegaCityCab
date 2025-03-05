package com.megacitycab.models;

public class SUV extends Vehicle {
    public SUV() {
        this.baseFare = 15;
        this.perKmFare = 3;
    }

    @Override
    public double calculateFare(double distance) {
        return baseFare + (perKmFare * distance);
    }
}
