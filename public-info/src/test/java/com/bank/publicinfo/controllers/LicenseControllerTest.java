package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LicenseControllerTest {
    @InjectMocks
    LicenseController licenseController;
    @Mock
    LicenseService licenseService;
    @Mock
    LicenseDto licenseDto;

    @Test
    void getAllLicense() {
        List<LicenseDto> licenseDtos = List.of(licenseDto, licenseDto);
        when(licenseService.getAllLicense()).thenReturn(licenseDtos);
        var result = licenseController.getAllLicense();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addLicense() {
        when(licenseService.addLicense(licenseDto)).thenReturn(licenseDto);
        var result = licenseController.addLicense(licenseDto);

        assertNotNull(result);
        assertEquals(licenseDto, result);
    }

    @Test
    void getLicenseById() {
        when(licenseService.findLicenseById(1L)).thenReturn(licenseDto);
        var result = licenseController.getLicenseById(1L);

        assertNotNull(result);
        assertEquals(licenseDto, result);
    }

    @Test
    void updateLicense() {
        when(licenseService.updateLicense(1L, licenseDto)).thenReturn(licenseDto);
        var result = licenseController.updateLicense(licenseDto, 1L);

        assertNotNull(result);
        assertEquals(licenseDto, result);
    }

    @Test
    void deleteLicense() {
        licenseController.deleteLicense(1L);

        verify(licenseService).deleteLicense(1L);
    }

    @Test
    void getAllLicenseByBank_detailsId() {
        List<LicenseDto> licenseDtos = List.of(licenseDto, licenseDto);
        when(licenseService.getAllLicenseByBank_detailsId(1L)).thenReturn(licenseDtos);
        var result = licenseController.getAllLicenseByBank_detailsId(1L);

        assertNotNull(result);
        assertEquals(licenseDtos, result);
    }
}