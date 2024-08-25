package com.insurance.premium.service;


import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.mapper.InsuranceCalculationMapper;
import com.insurance.premium.repository.InsuranceCalculationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceCalculationService {

    private final InsuranceCalculationRepository repository;
    private final InsuranceCalculationMapper mapper = InsuranceCalculationMapper.INSTANCE;


    public Optional<InsuranceCalculationDTO> calculateInsurance(InsuranceCalculationDTO insuranceCalculationDTO) {
        return Optional.ofNullable(insuranceCalculationDTO)
                .map(mapper::toInsuranceCalculation)
                .map(repository::save)
                .map(mapper::toInsuranceCalculationDTO);
    }

    public List<InsuranceCalculationDTO> getAll() {
        return mapper.toInsuranceCalculationDTOs(repository.findAll());
    }

    public Optional<InsuranceCalculationDTO> getById(Long id) {
        return repository.findById(id)
                .map(mapper::toInsuranceCalculationDTO);
    }

    public Optional<InsuranceCalculationDTO> update(Long id, InsuranceCalculationDTO insuranceCalculationDTO) {
        return repository.findById(id)
                .map(insuranceCalculation -> {
                    insuranceCalculation.setAnnualKilometers(insuranceCalculationDTO.annualKilometers());
                    insuranceCalculation.setPostcode(insuranceCalculationDTO.postcode());
                    insuranceCalculation.setRegistrationOffice(insuranceCalculationDTO.registrationOffice());
                    insuranceCalculation.setVehicleType(insuranceCalculationDTO.vehicleType());
                    insuranceCalculation.setCalculatedPremium(insuranceCalculationDTO.calculatedPremium());
                    return Optional.ofNullable(mapper.toInsuranceCalculationDTO(repository.save(insuranceCalculation)));
                }).orElseThrow();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
