package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/license")
@AllArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;

    @Operation(summary = "Get all licenses")
    @GetMapping
    public List<LicenseDto> getAllLicense() {
        return licenseService.getAllLicense();
    }

    @Operation(summary = "Add license")
    @PostMapping
    public LicenseDto addLicense(@RequestBody LicenseDto licenseDto) {
        return licenseService.addLicense(licenseDto);
    }

    @Operation(summary = "Get license by id")
    @GetMapping("/api/{id}")
    public LicenseDto getLicenseById(@PathVariable Long id) {
        return licenseService.findLicenseById(id);
    }

    @Operation(summary = "Update license by id")
    @PatchMapping("/{id}")
    public LicenseDto updateLicense(@RequestBody LicenseDto licenseDto, @PathVariable Long id) {
        return licenseService.updateLicense(id, licenseDto);
    }

    @Operation(summary = "Delete license by id")
    @DeleteMapping("/{id}")
    public void deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
    }

    @Operation(summary = "Get all licenses by bank_details id")
    @GetMapping("/allLicense/{id}")
    public List<LicenseDto> getAllLicenseByBank_detailsId(@PathVariable Long id) {
        return licenseService.getAllLicenseByBank_detailsId(id);
    }
}



