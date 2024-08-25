package com.insurance.premium.enums;

public enum VehicleType {

    CAR(1.0),
    TRUCK(1.2),
    MOTORCYCLE(1.3),
    BICYCLE(0.6),
    BUS(1.5),
    VAN(1.1),
    SUV(1.2),
    TRACTOR(1.4),
    SCOOTER(0.8),
    ATV(1.3),
    BOAT(1.4),
    AIRPLANE(1.5),
    TRAIN(1.4),
    HELICOPTER(1.5),
    SUBMARINE(1.5),
    SNOWMOBILE(1.2);

    private final double vehicleFactor;

    VehicleType(double vehicleFactor) {
        this.vehicleFactor = vehicleFactor;
    }

    public double getVehicleFactor() {
        return vehicleFactor;
    }
}