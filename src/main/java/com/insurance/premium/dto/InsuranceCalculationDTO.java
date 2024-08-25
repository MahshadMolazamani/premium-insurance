package com.insurance.premium.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.insurance.premium.enums.VehicleType;

public record InsuranceCalculationDTO(
        Long id,
        Integer annualKilometers,
        String postcode,
        String registrationOffice,
        @JsonDeserialize(using = VehicleTypeDeserializer.class) VehicleType vehicleType,
        Double calculatedPremium
) {
}
