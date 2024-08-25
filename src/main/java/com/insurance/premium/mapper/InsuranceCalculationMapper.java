package com.insurance.premium.mapper;

import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.entity.InsuranceCalculation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InsuranceCalculationMapper {

    InsuranceCalculationMapper INSTANCE = Mappers.getMapper(InsuranceCalculationMapper.class);

    InsuranceCalculationDTO toInsuranceCalculationDTO(InsuranceCalculation insuranceCalculation);

    InsuranceCalculation toInsuranceCalculation(InsuranceCalculationDTO insuranceCalculationDTO);

    List<InsuranceCalculationDTO> toInsuranceCalculationDTOs(List<InsuranceCalculation> insuranceCalculations);

    List<InsuranceCalculation> toInsuranceCalculations(List<InsuranceCalculationDTO> insuranceCalculationDTOS);
}
