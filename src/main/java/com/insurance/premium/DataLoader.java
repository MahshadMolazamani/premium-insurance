package com.insurance.premium;

import com.insurance.premium.service.RegionDataService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DataLoader implements ApplicationRunner {

    private final RegionDataService regionDataService;

    public DataLoader(RegionDataService regionDataService) {
        this.regionDataService = regionDataService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Path filePath = Paths.get(new ClassPathResource("data/postcodes.csv").getURI());
        regionDataService.bulkInsertFromCSV(filePath);
    }
}
