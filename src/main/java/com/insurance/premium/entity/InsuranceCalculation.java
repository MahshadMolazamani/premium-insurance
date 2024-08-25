package com.insurance.premium.entity;

import com.insurance.premium.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "insurance_calculation")
public class InsuranceCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annual_kilometers")
    private Integer annualKilometers;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "registration_office")
    private String registrationOffice;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "calculated_premium")
    private Double calculatedPremium;
}
