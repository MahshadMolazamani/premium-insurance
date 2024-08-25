package com.insurance.premium.controller;

import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.enums.BundeslandISO;
import com.insurance.premium.enums.VehicleType;
import com.insurance.premium.service.InsuranceCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InsuranceCalculationController.class)
class InsuranceCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceCalculationService insuranceCalculationService;

    private InsuranceCalculationDTO insuranceCalculationDTO;

    @BeforeEach
    void setUp() {
        double calculatedPremium = calculatePremium(10000, VehicleType.CAR, BundeslandISO.DE_BE);
        insuranceCalculationDTO = new InsuranceCalculationDTO(1L, 10000, "12345", "Berlin", VehicleType.CAR, calculatedPremium);
    }

    private double calculatePremium(int kilometers, VehicleType vehicleType, BundeslandISO region) {
        double kilometerFactor = getKilometerFactor(kilometers);
        return kilometerFactor * vehicleType.getVehicleFactor() * region.getRegionFactor();
    }

    private double getKilometerFactor(int kilometers) {
        if (kilometers <= 5000) {
            return 0.5;
        } else if (kilometers <= 10000) {
            return 1.0;
        } else if (kilometers <= 20000) {
            return 1.5;
        } else {
            return 2.0;
        }
    }

    @Test
    void calculateInsurance() throws Exception {
        when(insuranceCalculationService.calculateInsurance(any(InsuranceCalculationDTO.class))).thenReturn(Optional.of(insuranceCalculationDTO));

        mockMvc.perform(post("/insurance/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"annualKilometers\":10000,\"postcode\":\"12345\",\"registrationOffice\":\"Berlin\",\"vehicleType\":\"CAR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annualKilometers").value(10000))
                .andExpect(jsonPath("$.registrationOffice").value("Berlin"))
                .andExpect(jsonPath("$.calculatedPremium").value(insuranceCalculationDTO.calculatedPremium()));
    }

    @Test
    void getAllInsurances() throws Exception {
        when(insuranceCalculationService.getAll()).thenReturn(List.of(insuranceCalculationDTO));

        mockMvc.perform(get("/insurance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].annualKilometers").value(10000))
                .andExpect(jsonPath("$[0].registrationOffice").value("Berlin"))
                .andExpect(jsonPath("$[0].calculatedPremium").value(insuranceCalculationDTO.calculatedPremium()));
    }

    @Test
    void getInsuranceById() throws Exception {
        when(insuranceCalculationService.getById(anyLong())).thenReturn(Optional.of(insuranceCalculationDTO));

        mockMvc.perform(get("/insurance/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annualKilometers").value(10000))
                .andExpect(jsonPath("$.registrationOffice").value("Berlin"))
                .andExpect(jsonPath("$.calculatedPremium").value(insuranceCalculationDTO.calculatedPremium()));
    }

    @Test
    void updateInsurance() throws Exception {
        double updatedPremium = calculatePremium(15000, VehicleType.CAR, BundeslandISO.DE_BE);
        InsuranceCalculationDTO updatedDTO = new InsuranceCalculationDTO(1L, 15000, "12345", "Berlin", VehicleType.CAR, updatedPremium);

        when(insuranceCalculationService.update(anyLong(), any(InsuranceCalculationDTO.class))).thenReturn(Optional.of(updatedDTO));

        mockMvc.perform(put("/insurance/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"annualKilometers\":15000,\"postcode\":\"12345\",\"registrationOffice\":\"Berlin\",\"vehicleType\":\"CAR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.annualKilometers").value(15000))
                .andExpect(jsonPath("$.calculatedPremium").value(updatedPremium));
    }

    @Test
    void deleteInsurance() throws Exception {
        mockMvc.perform(delete("/insurance/1"))
                .andExpect(status().isNoContent());
    }
}
