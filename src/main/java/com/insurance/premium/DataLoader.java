package com.insurance.premium;

import com.insurance.premium.service.RegionDataService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Diese Komponente lädt initiale Daten in die Anwendung, sobald sie gestartet wird.
 * Sie implementiert das `ApplicationRunner`-Interface, das eine Ausführung nach dem Start der Anwendung ermöglicht.
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final RegionDataService regionDataService;

    /**
     * Konstruktor zur Injektion des RegionDataService.
     *
     * @param regionDataService Der Service zum Verarbeiten von Regionendaten.
     */
    public DataLoader(RegionDataService regionDataService) {
        this.regionDataService = regionDataService;
    }

    /**
     * Diese Methode wird nach dem Start der Anwendung ausgeführt und lädt Regionendaten aus einer CSV-Datei.
     *
     * @param args Die Anwendungsargumente, die beim Starten übergeben werden.
     * @throws Exception Wenn ein Fehler beim Laden der CSV-Datei auftritt.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Path filePath = Paths.get(new ClassPathResource("data/postcodes.csv").getURI());
        regionDataService.bulkInsertFromCSV(filePath);
    }
}
