package com.insurance.premium.service;


import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.entity.InsuranceCalculation;
import com.insurance.premium.enums.BundeslandISO;
import com.insurance.premium.repository.InsuranceCalculationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class InsuranceCalculationService {

    private final InsuranceCalculationRepository repository;

    public InsuranceCalculationService(InsuranceCalculationRepository repository) {
        this.repository = repository;
    }

    public Optional<InsuranceCalculationDTO> calculateInsurance(InsuranceCalculationDTO insuranceCalculationDTO) {

        InsuranceCalculation insuranceCalculation = toEntity(insuranceCalculationDTO);
        Double calculatePremium = calculatePremium(insuranceCalculation);
        insuranceCalculation.setCalculatedPremium(calculatePremium);

        return Optional.ofNullable(insuranceCalculation)
                .map(repository::save)
                .map(this::toDTO);
    }

    private Double calculatePremium(InsuranceCalculation calculation) {

        Double kilometerFactor = getKilometerFactor(calculation.getAnnualKilometers());
        Double vehicleFactor = calculation.getVehicleType().getVehicleFactor();
        Double regionFactor = BundeslandISO.fromBundesland(calculation.getRegistrationOffice()).getRegionFactor();

        return kilometerFactor * vehicleFactor * regionFactor;
    }

    private Double getKilometerFactor(Integer kilometers) {
        Double defaultKilometerFactor = 1.0;
        if (kilometers <= 5000) {
            return 0.5;
        } else if (kilometers <= 10000) {
            return 1.0;
        } else if (kilometers <= 20000) {
            return 1.5;
        } else if (kilometers > 20000) {
            return 2.0;
        }
        return defaultKilometerFactor;
    }

    public List<InsuranceCalculationDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InsuranceCalculationDTO> getById(Long id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<InsuranceCalculationDTO> update(Long id, InsuranceCalculationDTO insuranceCalculationDTO) {
        Optional<InsuranceCalculation> existingInsuranceCalculation = repository.findById(id);
        InsuranceCalculation updatedInsuranceCalculation = toEntity(insuranceCalculationDTO);
        if (existingInsuranceCalculation.isPresent()) {

            updatedInsuranceCalculation.setId(id);
            Double calculatePremium = calculatePremium(updatedInsuranceCalculation);
            updatedInsuranceCalculation.setCalculatedPremium(calculatePremium);
        }
        InsuranceCalculation savedInsuranceCalculation = repository.save(updatedInsuranceCalculation);
        return Optional.ofNullable(toDTO(savedInsuranceCalculation));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private InsuranceCalculation toEntity(InsuranceCalculationDTO insuranceCalculationDTO) {
        InsuranceCalculation insuranceCalculation = new InsuranceCalculation();
        insuranceCalculation.setId(insuranceCalculationDTO.id());
        insuranceCalculation.setAnnualKilometers(insuranceCalculationDTO.annualKilometers());
        insuranceCalculation.setPostcode(insuranceCalculationDTO.postcode());
        insuranceCalculation.setRegistrationOffice(insuranceCalculationDTO.registrationOffice());
        insuranceCalculation.setVehicleType(insuranceCalculationDTO.vehicleType());
        insuranceCalculation.setCalculatedPremium(insuranceCalculationDTO.calculatedPremium());

        return insuranceCalculation;
    }

    private InsuranceCalculationDTO toDTO(InsuranceCalculation insuranceCalculation) {
        return new InsuranceCalculationDTO(
                insuranceCalculation.getId(),
                insuranceCalculation.getAnnualKilometers(),
                insuranceCalculation.getPostcode(),
                insuranceCalculation.getRegistrationOffice(),
                insuranceCalculation.getVehicleType(),
                insuranceCalculation.getCalculatedPremium()
        );
    }

}
