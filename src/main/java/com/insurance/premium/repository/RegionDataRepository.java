package com.insurance.premium.repository;


import com.insurance.premium.entity.RegionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionDataRepository extends JpaRepository<RegionData, Long> {

    Optional<RegionData> findByPostleitzahl(String postleitzahl);
}
