package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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
class CertificateControllerTest {
    @InjectMocks
    CertificateController certificateController;
    @Mock
    CertificateService certificateService;
    @Mock
    CertificateDto certificateDto;

    @Test
    void getAllCertificate() {
        List<CertificateDto> certificateDtos = List.of(certificateDto, certificateDto);
        when(certificateService.getAllCertificates()).thenReturn(certificateDtos);
        var result = certificateController.getAllCertificate();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addCertificate() {
        when(certificateService.addCertificate(certificateDto)).thenReturn(certificateDto);
        var result = certificateController.addCertificate(certificateDto);

        assertNotNull(result);
        assertEquals(certificateDto, result);
    }

    @Test
    void getCertificateById() {
        when(certificateService.findCertificateById(1L)).thenReturn(certificateDto);
        var result = certificateController.getCertificateById(1L);

        assertNotNull(result);
        assertEquals(certificateDto, result);
    }

    @Test
    void updateCertificate() {
        when(certificateService.updateCertificate(1L, certificateDto)).thenReturn(certificateDto);
        var result = certificateController.updateCertificate(certificateDto, 1L);

        assertNotNull(result);
        assertEquals(certificateDto, result);
    }

    @Test
    void deleteCertificate() {
        certificateController.deleteCertificate(1L);

        verify(certificateService).deleteCertificate(1L);
    }

    @Test
    void getAllCertificatesByBank_detailsId() {
        List<CertificateDto> certificateDtos = List.of(certificateDto, certificateDto);
        when(certificateService.getAllCertificatesByBank_detailsId(1L)).thenReturn(certificateDtos);
        var result = certificateController.getAllCertificatesByBank_detailsId(1L);

        assertNotNull(result);
        assertEquals(certificateDtos, result);
    }
}