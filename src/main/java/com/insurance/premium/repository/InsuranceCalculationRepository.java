package com.insurance.premium.repository;


import com.insurance.premium.entity.InsuranceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCalculationRepository extends JpaRepository<InsuranceCalculation, Long> {
}
