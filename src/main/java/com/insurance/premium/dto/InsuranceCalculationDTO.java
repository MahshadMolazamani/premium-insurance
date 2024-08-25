package com.insurance.premium.dto;

public record InsuranceCalculationDTO(
        Long id,
        Integer annualKilometers,
        String postcode,
        String registrationOffice,
        String vehicleType,
        Double calculatedPremium
) {
}
