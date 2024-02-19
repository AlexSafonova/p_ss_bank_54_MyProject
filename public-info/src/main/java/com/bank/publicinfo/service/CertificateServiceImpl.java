package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.validation.CertificateValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CertificateValidation certificateValidation;

    public List<CertificateDto> getAllCertificates() {
        return certificateMapper.toListCertificateDto(certificateRepository.findAll());
    }

    public CertificateDto findCertificateById(Long id) {
        return certificateMapper.toCertificateDto(certificateValidation.findCertificateValidator(id));
    }

    public CertificateDto addCertificate(CertificateDto certificateDto) {
        return certificateMapper.toCertificateDto(certificateRepository.save
                (certificateValidation.createCertificateValidator(certificateMapper.toCertificate(certificateDto))));
    }

    public CertificateDto updateCertificate(Long id, CertificateDto certificateDto) {
        certificateValidation.findCertificateValidator(id);
        return certificateMapper.toCertificateDto(certificateRepository.save
                (certificateValidation.createCertificateValidator(certificateMapper.toCertificate(certificateDto))));
    }

    public void deleteCertificate(Long id) {
        certificateRepository.delete(certificateValidation.findCertificateValidator(id));
    }

    public List<CertificateDto> getAllCertificatesByBank_detailsId(Long bank_details_id) {
        return certificateMapper.toListCertificateDto(certificateRepository.findAllCertificatesByBankDetailsId(bank_details_id));
    }
}
