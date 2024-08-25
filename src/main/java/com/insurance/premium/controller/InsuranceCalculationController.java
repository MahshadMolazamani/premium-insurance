package com.insurance.premium.controller;


import com.insurance.premium.dto.InsuranceCalculationDTO;
import com.insurance.premium.service.InsuranceCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
@Tag(name = "Insurance Calculation", description = "API for calculating insurance premium")
public class InsuranceCalculationController {

    private InsuranceCalculationService insuranceCalculationService;

    @PostMapping("/calculate")
    @Operation(summary = "Calculate a new Insurance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculating a new Insurance was successful", content = @Content(schema = @Schema(implementation = InsuranceCalculationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<InsuranceCalculationDTO> calculateInsurance(@RequestBody InsuranceCalculationDTO insuranceCalculationDTO) {
        return insuranceCalculationService.calculateInsurance(insuranceCalculationDTO)
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @GetMapping
    @Operation(summary = "Get all calculated Insurances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all calculated Insurances", content = @Content(schema = @Schema(implementation = InsuranceCalculationDTO.class)))
    })
    public ResponseEntity<List<InsuranceCalculationDTO>> getAll() {
        var calculatedInsurances = insuranceCalculationService.getAll();
        return ResponseEntity.ok(calculatedInsurances);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a calculated Insurance by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "calculated Insurance found", content = @Content(schema = @Schema(implementation = InsuranceCalculationDTO.class))),
            @ApiResponse(responseCode = "404", description = "calculated Insurance not found")
    })
    public ResponseEntity<InsuranceCalculationDTO> getById(@PathVariable Long id) {
        return insuranceCalculationService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a calculated Insurance by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "calculated Insurance updated successfully", content = @Content(schema = @Schema(implementation = InsuranceCalculationDTO.class))),
            @ApiResponse(responseCode = "404", description = "calculated Insurance not found")
    })
    public ResponseEntity<InsuranceCalculationDTO> update(@PathVariable Long id, @RequestBody InsuranceCalculationDTO insuranceCalculationDTO) {
        Optional<InsuranceCalculationDTO> savedCalculationDTO = insuranceCalculationService.update(id, insuranceCalculationDTO);
        return savedCalculationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a calculated Insurance by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "calculated Insurance deleted successfully"),
            @ApiResponse(responseCode = "404", description = "calculated Insurance not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        insuranceCalculationService.delete(id);
        return noContent().build();
    }

}
