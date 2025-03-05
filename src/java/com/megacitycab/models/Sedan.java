package com.megacitycab.models;

public class Sedan extends Vehicle {
    public Sedan() {
        this.baseFare = 10;
        this.perKmFare = 2;
    }

    @Override
    public double calculateFare(double distance) {
        return baseFare + (perKmFare * distance);
    }
}
