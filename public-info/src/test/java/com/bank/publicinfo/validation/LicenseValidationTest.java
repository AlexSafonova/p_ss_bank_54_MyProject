package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.LicenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class LicenseValidationTest {
    @InjectMocks
    LicenseValidation licenseValidation;
    @Mock
    LicenseRepository licenseRepository;
    Byte[] bytes = new Byte[]{1, 2, 3, 4};
    License license1 = new License(1L, bytes, null);
    License license2 = new License(1L, null, null);

    @Test
    void createLicenseValidator() {
        var result = licenseValidation.createLicenseValidator(license1);

        assertNotNull(result);
        assertEquals(license1, result);
    }

    @Test
    void createLicenseValidatorGetException() {
        assertThrows(ValidatorException.class, () -> licenseValidation.createLicenseValidator(null));
    }

    @Test
    void createLicenseValidatorGetExceptionNullPhoto() {
        assertThrows(ValidatorException.class, () -> licenseValidation.createLicenseValidator(license2));
    }

    @Test
    void findLicenseValidator() {
        Optional<License> license = Optional.ofNullable(license1);
        doReturn(license).when(licenseRepository).findById(1L);
        var result = licenseValidation.findLicenseValidator(1L);

        assertNotNull(result);
        assertEquals(license1, result);
    }

    @Test
    void findLicenseValidatorGetException() {
        Optional<License> license2 = Optional.empty();
        doReturn(license2).when(licenseRepository).findById(1L);

        assertThrows(NotFoundException.class, () -> licenseValidation.findLicenseValidator(1L));
    }
}