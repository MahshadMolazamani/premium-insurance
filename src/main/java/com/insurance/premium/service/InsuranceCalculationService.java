package com.insurance.premium.service;


import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.entity.InsuranceCalculation;
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
        return Optional.ofNullable(insuranceCalculationDTO)
                .map(this::toEntity)
                .map(repository::save)
                .map(this::toDTO);
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
        return repository.findById(id)
                .map(existingInsuranceCalculation -> {
                    InsuranceCalculation updatedInsuranceCalculation = toEntity(insuranceCalculationDTO);
                    updatedInsuranceCalculation.setId(id);
                    InsuranceCalculation savedInsuranceCalculation = repository.save(updatedInsuranceCalculation);
                    return Optional.ofNullable(toDTO(savedInsuranceCalculation));
                }).orElseThrow();
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
