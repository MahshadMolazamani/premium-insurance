package com.insurance.premium.service;

import com.insurance.premium.entity.Location;
import com.insurance.premium.entity.RegionData;
import com.insurance.premium.repository.RegionDataRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Service-Klasse zur Verwaltung von RegionData-Entitäten.
 * Diese Klasse enthält Logik zum Massenimport von Regionsdaten aus einer CSV-Datei.
 */
@Service
@Log4j2
public class RegionDataService {

    private final RegionDataRepository regionDataRepository;

    /**
     * Konstruktor zur Injektion des RegionDataRepository.
     *
     * @param regionDataRepository Repository für die RegionData-Entitäten.
     */
    public RegionDataService(RegionDataRepository regionDataRepository) {
        this.regionDataRepository = regionDataRepository;
    }

    /**
     * Führt einen Massenimport von RegionData-Entitäten aus einer CSV-Datei durch.
     * Die CSV-Datei wird zeilenweise gelesen und die Daten werden in die Datenbank eingefügt.
     *
     * @param csvFilePath Der Pfad zur CSV-Datei, die die RegionData-Daten enthält.
     */
    @Transactional
    public void bulkInsertFromCSV(Path csvFilePath) {
        try (BufferedReader reader = Files.newBufferedReader(csvFilePath)) {
            String line;
            List<RegionData> regionDataList = new ArrayList<>();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                if (columns.length < 16) {
                    log.error("this line of csv maybe invalid: {}", line);
                    continue;
                }

                RegionData regionData = new RegionData();

                regionData.setBundeslandIso(columns[1]);
                regionData.setBundesland(columns[2]);
                regionData.setLand(columns[4]);
                regionData.setStadt(columns[5]);
                regionData.setPostleitzahl(columns[6]);

                Location location = new Location(Double.valueOf(columns[10]), Double.valueOf(columns[11]));
                regionData.setLocation(location);

                regionDataList.add(regionData);

                if (regionDataList.size() % 1000 == 0) {
                    regionDataRepository.saveAll(regionDataList);
                    regionDataList.clear();
                }
            }
            if (!regionDataList.isEmpty()) {
                regionDataRepository.saveAll(regionDataList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
