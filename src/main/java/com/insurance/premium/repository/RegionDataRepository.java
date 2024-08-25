package com.insurance.premium.repository;


import com.insurance.premium.entity.RegionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionDataRepository extends JpaRepository<RegionData, Long> {
}
