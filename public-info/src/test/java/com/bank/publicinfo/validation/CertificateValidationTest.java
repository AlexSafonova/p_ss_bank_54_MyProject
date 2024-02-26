package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.CertificateRepository;
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
class CertificateValidationTest {
    @InjectMocks
    CertificateValidation certificateValidation;
    @Mock
    CertificateRepository certificateRepository;


    Byte[] bytes = new Byte[]{1, 2, 3, 4};
    Certificate certificate1 = new Certificate(1L, bytes, null);
    Certificate certificate2 = new Certificate(1L, null, null);


    @Test
    void createCertificateValidator() {
        var result = certificateValidation.createCertificateValidator(certificate1);

        assertNotNull(result);
        assertEquals(certificate1, result);
    }

    @Test
    void createCertificateValidatorGetException() {
        assertThrows(ValidatorException.class, () -> certificateValidation.createCertificateValidator(null));
    }

    @Test
    void createCertificateValidatorGetExceptionNullPhoto() {
        assertThrows(ValidatorException.class, () -> certificateValidation.createCertificateValidator(certificate2));
    }


    @Test
    void findCertificateValidator() {
        Optional<Certificate> certificate = Optional.ofNullable(certificate1);
        doReturn(certificate).when(certificateRepository).findById(1L);
        var result = certificateValidation.findCertificateValidator(1L);

        assertNotNull(result);
        assertEquals(certificate1, result);
    }

    @Test
    void findCertificateValidatorGetException() {
        Optional<Certificate> certificate2 = Optional.empty();
        doReturn(certificate2).when(certificateRepository).findById(1L);

        assertThrows(NotFoundException.class, () -> certificateValidation.findCertificateValidator(1L));

    }
}