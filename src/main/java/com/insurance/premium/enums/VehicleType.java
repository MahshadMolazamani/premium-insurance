package com.insurance.premium.enums;

/**
 * Dieses Enum repräsentiert verschiedene Fahrzeugtypen mit einem entsprechenden Faktor,
 * der bei der Berechnung der Versicherungsprämie verwendet wird.
 */
public enum VehicleType {

    DEFAULT_FACTOR(1.0),
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

    /**
     * Konstruktor für das Enum VehicleType.
     *
     * @param vehicleFactor Der Faktor, der bei der Berechnung der Versicherungsprämie für diesen Fahrzeugtyp verwendet wird.
     */
    VehicleType(double vehicleFactor) {
        this.vehicleFactor = vehicleFactor;
    }

    /**
     * Gibt den Faktor für den Fahrzeugtyp zurück.
     *
     * @return Der Fahrzeugfaktor.
     */
    public double getVehicleFactor() {
        return vehicleFactor;
    }
}