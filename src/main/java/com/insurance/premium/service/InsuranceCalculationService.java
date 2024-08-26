package com.insurance.premium.service;


import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.entity.InsuranceCalculation;
import com.insurance.premium.enums.BundeslandISO;
import com.insurance.premium.repository.InsuranceCalculationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service-Klasse für die Verarbeitung der Logik der Versicherungsberechnung.
 */
@Service
public class InsuranceCalculationService {

    private final InsuranceCalculationRepository repository;

    /**
     * Konstruktor zur Injektion von Abhängigkeiten.
     *
     * @param insuranceCalculationRepository Repository für Versicherungsberechnungs-Entitäten.
     */
    public InsuranceCalculationService(InsuranceCalculationRepository repository) {
        this.repository = repository;
    }

    /**
     * Berechnet die Versicherungsprämie basierend auf dem bereitgestellten Data Transfer Object (DTO).
     * Die berechnete Prämie wird gespeichert und als DTO zurückgegeben.
     *
     * @param insuranceCalculationDTO Data Transfer Object, das die Details der Versicherungsberechnung enthält.
     * @return Ein Optional, das das gespeicherte InsuranceCalculationDTO mit der berechneten Prämie enthält, oder ein leeres Optional, falls die Operation fehlgeschlagen ist.
     */
    public Optional<InsuranceCalculationDTO> calculateInsurance(InsuranceCalculationDTO insuranceCalculationDTO) {

        InsuranceCalculation insuranceCalculation = toEntity(insuranceCalculationDTO);
        Double calculatePremium = calculatePremium(insuranceCalculation);
        insuranceCalculation.setCalculatedPremium(calculatePremium);

        return Optional.ofNullable(insuranceCalculation)
                .map(repository::save)
                .map(this::toDTO);
    }

    /**
     * Führt die Prämienberechnung basierend auf der gegebenen InsuranceCalculation-Entität durch.
     * Die Berechnung basiert auf den jährlichen Kilometern, dem Fahrzeugtyp und der Region der Zulassung.
     *
     * @param calculation Die InsuranceCalculation-Entität, die die notwendigen Daten für die Berechnung enthält.
     * @return Die berechnete Versicherungsprämie.
     */
    private Double calculatePremium(InsuranceCalculation calculation) {

        Double kilometerFactor = getKilometerFactor(calculation.getAnnualKilometers());
        Double vehicleFactor = calculation.getVehicleType().getVehicleFactor();
        Double regionFactor = BundeslandISO.fromBundesland(calculation.getRegistrationOffice()).getRegionFactor();

        return kilometerFactor * vehicleFactor * regionFactor;
    }

    /**
     * Bestimmt den Faktor, der bei der Prämienberechnung basierend auf den jährlich gefahrenen Kilometern verwendet wird.
     *
     * @param kilometers Die Anzahl der jährlich gefahrenen Kilometer.
     * @return Der entsprechende Kilometerfaktor.
     */
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

    /**
     * Ruft alle InsuranceCalculation-Entitäten ab und konvertiert sie in DTOs.
     *
     * @return Eine Liste von InsuranceCalculationDTOs, die alle Versicherungsberechnungen im Repository darstellen.
     */
    public List<InsuranceCalculationDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ruft eine InsuranceCalculation-Entität anhand ihrer ID ab und konvertiert sie in ein DTO.
     *
     * @param id Die ID der InsuranceCalculation-Entität.
     * @return Ein Optional, das das entsprechende InsuranceCalculationDTO enthält, oder ein leeres Optional, falls nicht gefunden.
     */
    public Optional<InsuranceCalculationDTO> getById(Long id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    /**
     * Aktualisiert eine vorhandene InsuranceCalculation-Entität, die durch ihre ID identifiziert wird, mit den Daten aus dem bereitgestellten DTO.
     * Die aktualisierte Entität wird gespeichert und als DTO zurückgegeben.
     *
     * @param id Die ID der vorhandenen InsuranceCalculation-Entität.
     * @param insuranceCalculationDTO Data Transfer Object, das die aktualisierten Details der Versicherungsberechnung enthält.
     * @return Ein Optional, das das aktualisierte InsuranceCalculationDTO enthält, oder ein leeres Optional, falls die Aktualisierung fehlgeschlagen ist.
     */
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

    /**
     * Löscht eine InsuranceCalculation-Entität anhand ihrer ID.
     *
     * @param id Die ID der zu löschenden InsuranceCalculation-Entität.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Konvertiert ein InsuranceCalculationDTO in eine InsuranceCalculation-Entität.
     *
     * @param insuranceCalculationDTO Das zu konvertierende Data Transfer Object.
     * @return Die entsprechende InsuranceCalculation-Entität.
     */
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

    /**
     * Konvertiert eine InsuranceCalculation-Entität in ein InsuranceCalculationDTO.
     *
     * @param insuranceCalculation Die zu konvertierende Entität.
     * @return Das entsprechende InsuranceCalculationDTO.
     */
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
