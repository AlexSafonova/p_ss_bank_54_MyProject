package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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
@RequestMapping("/certificate")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @Operation(summary = "Get all certificates")
    @GetMapping
    public List<CertificateDto> getAllCertificate() {
        return certificateService.getAllCertificates();
    }

    @Operation(summary = "Add certificate")
    @PostMapping
    public CertificateDto addCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateService.addCertificate(certificateDto);
    }

    @Operation(summary = "Get certificate by id")
    @GetMapping("/{id}")
    public CertificateDto getCertificateById(@PathVariable Long id) {
        return certificateService.findCertificateById(id);
    }

    @Operation(summary = "Update certificate by id")
    @PatchMapping("/{id}")
    public CertificateDto updateCertificate(@RequestBody CertificateDto certificateDto, @PathVariable Long id) {
        return certificateService.updateCertificate(id, certificateDto);
    }

    @Operation(summary = "Delete certificate by id")
    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
    }

    @Operation(summary = "Get all certificates by bank_details id")
    @GetMapping("/allCertificates/{id}")
    public List<CertificateDto> getAllCertificatesByBank_detailsId(@PathVariable Long id) {
        return certificateService.getAllCertificatesByBank_detailsId(id);
    }
}

