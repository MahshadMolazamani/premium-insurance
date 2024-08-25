package com.insurance.premium.dto;

import com.insurance.premium.enums.VehicleType;

public record InsuranceCalculationDTO(
        Long id,
        Integer annualKilometers,
        String postcode,
        String registrationOffice,
        VehicleType vehicleType,
        Double calculatedPremium
) {
}
