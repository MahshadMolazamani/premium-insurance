package com.insurance.premium.entity;

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
    private String vehicleType;

    @Column(name = "calculated_premium")
    private Double calculatedPremium;
}
