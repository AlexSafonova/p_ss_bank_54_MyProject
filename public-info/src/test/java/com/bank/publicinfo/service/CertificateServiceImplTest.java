package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.validation.CertificateValidation;
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
class CertificateServiceImplTest {
    @InjectMocks
    CertificateServiceImpl certificateService;
    @Mock
    CertificateRepository certificateRepository;
    @Mock
    CertificateValidation certificateValidation;
    @Mock
    CertificateMapper certificateMapper;
    Byte[] bytes = new Byte[]{1, 2, 3, 4};
    Certificate certificate1 = new Certificate(1L, bytes, null);
    Certificate certificate2 = new Certificate(2L, bytes, null);
    CertificateDto certificateDto1 = new CertificateDto(1L, bytes);
    CertificateDto certificateDto2 = new CertificateDto(2L, bytes);

    @Test
    void getAllCertificates() {
        List<Certificate> certificates = List.of(certificate1, certificate2);
        List<CertificateDto> certificateDtos = List.of(certificateDto1, certificateDto2);
        when(certificateRepository.findAll()).thenReturn(certificates);
        when(certificateMapper.toListCertificateDto(certificates)).thenReturn(certificateDtos);
        var result = certificateService.getAllCertificates();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findCertificateById() {
        when(certificateValidation.findCertificateValidator(1L)).thenReturn(certificate1);
        doReturn(certificateDto1).when(certificateMapper).toCertificateDto(certificate1);


        var result = certificateService.findCertificateById(1L);

        assertNotNull(result);
        assertEquals(certificateDto1, result);
    }

    @Test
    void addCertificate() {
        doReturn(certificate1).when(certificateMapper).toCertificate(certificateDto1);
        when(certificateValidation.createCertificateValidator(certificate1)).thenReturn(certificate1);
        doReturn(certificateDto1).when(certificateMapper).toCertificateDto(certificate1);
        doReturn(certificate1).when(certificateRepository).save(certificate1);

        var result = certificateService.addCertificate(certificateDto1);

        assertNotNull(result);
        assertEquals(certificateDto1, result);
    }

    @Test
    void updateCertificate() {
        when(certificateValidation.findCertificateValidator(1L)).thenReturn(certificate1);
        doReturn(certificate1).when(certificateMapper).toCertificate(certificateDto1);
        when(certificateValidation.createCertificateValidator(certificate1)).thenReturn(certificate1);
        doReturn(certificateDto1).when(certificateMapper).toCertificateDto(certificate1);
        doReturn(certificate1).when(certificateRepository).save(certificate1);

        var result = certificateService.updateCertificate(1L, certificateDto1);

        assertNotNull(result);
        assertEquals(certificateDto1, result);
    }

    @Test
    void deleteCertificate() {
        when(certificateValidation.findCertificateValidator(1L)).thenReturn(certificate1);
        certificateService.deleteCertificate(1L);

        verify(certificateRepository).delete(certificate1);
    }

    @Test
    void getAllCertificatesByBank_detailsId() {
        certificateService.getAllCertificatesByBank_detailsId(1L);

        verify(certificateRepository).findAllCertificatesByBankDetailsId(1L);


    }
}