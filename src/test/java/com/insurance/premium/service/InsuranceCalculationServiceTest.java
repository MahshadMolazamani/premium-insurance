package com.insurance.premium.service;

import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.entity.InsuranceCalculation;
import com.insurance.premium.enums.BundeslandISO;
import com.insurance.premium.enums.VehicleType;
import com.insurance.premium.repository.InsuranceCalculationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InsuranceCalculationServiceTest {

    @Mock
    private InsuranceCalculationRepository repository;

    @InjectMocks
    private InsuranceCalculationService insuranceCalculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateInsuranceForCarWith5000KmInBerlin() {
        // Arrange
        int kilometers = 5000;
        InsuranceCalculationDTO dto = new InsuranceCalculationDTO(1L, kilometers, "12345", "Berlin", VehicleType.CAR, null);
        InsuranceCalculation savedEntity = new InsuranceCalculation();
        savedEntity.setId(1L);

        // Berlin has a region factor of 0.75, CAR has a vehicle factor of 1.0, and 5000 km gives a kilometer factor of 0.5
        double expectedPremium = 0.5 * VehicleType.CAR.getVehicleFactor() * BundeslandISO.DE_BE.getRegionFactor();
        savedEntity.setCalculatedPremium(expectedPremium);

        when(repository.save(any(InsuranceCalculation.class))).thenReturn(savedEntity);

        // Act
        Optional<InsuranceCalculationDTO> result = insuranceCalculationService.calculateInsurance(dto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedEntity.getId(), result.get().id());
        assertEquals(expectedPremium, result.get().calculatedPremium());
    }

    @Test
    void testCalculateInsuranceForMotorcycleWith15000KmInBayern() {
        // Arrange
        int kilometers = 15000;
        InsuranceCalculationDTO dto = new InsuranceCalculationDTO(2L, kilometers, "12345", "Bayern", VehicleType.MOTORCYCLE, null);
        InsuranceCalculation savedEntity = new InsuranceCalculation();
        savedEntity.setId(2L);

        // Bayern has a region factor of 1.4, MOTORCYCLE has a vehicle factor of 1.3, and 15000 km gives a kilometer factor of 1.5
        double expectedPremium = 1.5 * VehicleType.MOTORCYCLE.getVehicleFactor() * BundeslandISO.DE_BY.getRegionFactor();
        savedEntity.setCalculatedPremium(expectedPremium);

        when(repository.save(any(InsuranceCalculation.class))).thenReturn(savedEntity);

        // Act
        Optional<InsuranceCalculationDTO> result = insuranceCalculationService.calculateInsurance(dto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedEntity.getId(), result.get().id());
        assertEquals(expectedPremium, result.get().calculatedPremium());
    }

    @Test
    void testCalculateInsuranceForTruckWith20001KmInBremen() {
        // Arrange
        int kilometers = 20001;
        InsuranceCalculationDTO dto = new InsuranceCalculationDTO(3L, kilometers, "12345", "Bremen", VehicleType.TRUCK, null);
        InsuranceCalculation savedEntity = new InsuranceCalculation();
        savedEntity.setId(3L);

        // Bremen has a region factor of 0.8, TRUCK has a vehicle factor of 1.2, and 20001 km gives a kilometer factor of 2.0
        double expectedPremium = 2.0 * VehicleType.TRUCK.getVehicleFactor() * BundeslandISO.DE_HB.getRegionFactor();
        savedEntity.setCalculatedPremium(expectedPremium);

        when(repository.save(any(InsuranceCalculation.class))).thenReturn(savedEntity);

        // Act
        Optional<InsuranceCalculationDTO> result = insuranceCalculationService.calculateInsurance(dto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedEntity.getId(), result.get().id());
        assertEquals(expectedPremium, result.get().calculatedPremium());
    }

    @Test
    void testGetAll() {
        // Arrange
        InsuranceCalculation calculation = new InsuranceCalculation();
        calculation.setId(1L);
        when(repository.findAll()).thenReturn(List.of(calculation));

        // Act
        List<InsuranceCalculationDTO> result = insuranceCalculationService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
    }

    @Test
    void testGetById() {
        // Arrange
        InsuranceCalculation calculation = new InsuranceCalculation();
        calculation.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(calculation));

        // Act
        Optional<InsuranceCalculationDTO> result = insuranceCalculationService.getById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
    }

    @Test
    void testUpdate() {
        // Arrange
        InsuranceCalculationDTO dto = new InsuranceCalculationDTO(1L, 10000, "12345", "Berlin", VehicleType.CAR, null);
        InsuranceCalculation existingCalculation = new InsuranceCalculation();
        existingCalculation.setId(1L);

        // Updating the premium calculation with Berlin and CAR factors
        double expectedPremium = 1.0 * VehicleType.CAR.getVehicleFactor() * BundeslandISO.DE_BE.getRegionFactor();
        existingCalculation.setCalculatedPremium(expectedPremium);

        when(repository.findById(1L)).thenReturn(Optional.of(existingCalculation));
        when(repository.save(any(InsuranceCalculation.class))).thenReturn(existingCalculation);

        // Act
        Optional<InsuranceCalculationDTO> result = insuranceCalculationService.update(1L, dto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedPremium, result.get().calculatedPremium());
        verify(repository, times(1)).save(any(InsuranceCalculation.class));
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        insuranceCalculationService.delete(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}
