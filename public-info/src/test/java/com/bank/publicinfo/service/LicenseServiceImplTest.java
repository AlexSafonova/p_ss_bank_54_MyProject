package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.validation.LicenseValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LicenseServiceImplTest {
    @InjectMocks
    LicenseServiceImpl licenseService;
    @Mock
    LicenseRepository licenseRepository;
    @Mock
    LicenseValidation licenseValidation;
    @Mock
    LicenseMapper licenseMapper;
    Byte[] bytes = new Byte[]{1, 2, 3, 4};
    License license1 = new License(1L, bytes, null);
    License license2 = new License(2L, bytes, null);
    LicenseDto licenseDto1 = new LicenseDto(1L, bytes);
    LicenseDto licenseDto2 = new LicenseDto(1L, bytes);

    @Test
    void getAllLicense() {
        List<License> licenses = List.of(license1, license2);
        List<LicenseDto> licenseDtos = List.of(licenseDto1, licenseDto2);
        when(licenseRepository.findAll()).thenReturn(licenses);
        when(licenseMapper.toListLicenseDto(licenses)).thenReturn(licenseDtos);
        var result = licenseService.getAllLicense();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findLicenseById() {
        when(licenseValidation.findLicenseValidator(1L)).thenReturn(license1);
        doReturn(licenseDto1).when(licenseMapper).toLicenseDto(license1);


        var result = licenseService.findLicenseById(1L);

        assertNotNull(result);
        assertEquals(licenseDto1, result);
    }

    @Test
    void addLicense() {
        doReturn(license1).when(licenseMapper).toLicense(licenseDto1);
        when(licenseValidation.createLicenseValidator(license1)).thenReturn(license1);
        doReturn(licenseDto1).when(licenseMapper).toLicenseDto(license1);
        doReturn(license1).when(licenseRepository).save(license1);

        var result = licenseService.addLicense(licenseDto1);

        assertNotNull(result);
        assertEquals(licenseDto1, result);
    }

    @Test
    void updateLicense() {
        when(licenseValidation.findLicenseValidator(1L)).thenReturn(license1);
        doReturn(license1).when(licenseMapper).toLicense(licenseDto1);
        when(licenseValidation.createLicenseValidator(license1)).thenReturn(license1);
        doReturn(licenseDto1).when(licenseMapper).toLicenseDto(license1);
        doReturn(license1).when(licenseRepository).save(license1);

        var result = licenseService.updateLicense(1L, licenseDto1);

        assertNotNull(result);
        assertEquals(licenseDto1, result);
    }

    @Test
    void deleteLicense() {
        when(licenseValidation.findLicenseValidator(1L)).thenReturn(license1);
        licenseService.deleteLicense(1L);

        verify(licenseRepository).delete(license1);
    }

    @Test
    void getAllLicenseByBank_detailsId() {
        licenseService.getAllLicenseByBank_detailsId(1L);

        verify(licenseRepository).findAllLicenseByBankDetailsId(1L);
    }
}