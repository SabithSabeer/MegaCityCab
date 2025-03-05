package com.megacitycab.models;

public abstract class Vehicle {
    protected double baseFare;
    protected double perKmFare;

    public abstract double calculateFare(double distance);
}
