package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CertificateService {
    List<CertificateDto> getAllCertificates();

    CertificateDto findCertificateById(Long id);

    CertificateDto addCertificate(CertificateDto certificateDto);

    CertificateDto updateCertificate(Long id, CertificateDto certificateDto);

    void deleteCertificate(Long id);

    List<CertificateDto> getAllCertificatesByBank_detailsId(Long bank_details_id);
}
