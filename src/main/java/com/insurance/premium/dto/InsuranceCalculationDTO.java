package com.insurance.premium.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.insurance.premium.enums.VehicleType;

/**
 * Data Transfer Object (DTO) für die Berechnung der Versicherungsprämie.
 * Enthält alle notwendigen Informationen, um eine Prämienberechnung durchzuführen.
 */
public record InsuranceCalculationDTO(
        Long id,
        Integer annualKilometers,
        String postcode,
        String registrationOffice,
        @JsonDeserialize(using = VehicleTypeDeserializer.class) VehicleType vehicleType,
        Double calculatedPremium
) {
}
