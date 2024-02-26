package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.validation.LicenseValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private final LicenseMapper licenseMapper;
    private final LicenseRepository licenseRepository;
    private final LicenseValidation licenseValidation;

    public List<LicenseDto> getAllLicense() {
        return licenseMapper.toListLicenseDto(licenseRepository.findAll());
    }

    public LicenseDto findLicenseById(Long id) {
        return licenseMapper.toLicenseDto(licenseValidation.findLicenseValidator(id));
    }

    public LicenseDto addLicense(LicenseDto licenseDto) {
        License license = validateAndCreateLicense(licenseDto);
        return licenseMapper.toLicenseDto(licenseRepository.save(license));
    }

    public LicenseDto updateLicense(Long id, LicenseDto licenseDto) {
        licenseValidation.findLicenseValidator(id);
        License license = validateAndCreateLicense(licenseDto);
        return licenseMapper.toLicenseDto(licenseRepository.save(license));
    }

    public void deleteLicense(Long id) {
        licenseRepository.delete(licenseValidation.findLicenseValidator(id));
    }

    public List<LicenseDto> getAllLicenseByBank_detailsId(Long bank_details_id) {
        return licenseMapper.toListLicenseDto(licenseRepository.findAllLicenseByBankDetailsId(bank_details_id));
    }

    private License validateAndCreateLicense(LicenseDto licenseDto) {
        return licenseValidation.createLicenseValidator(licenseMapper.toLicense(licenseDto));
    }
}
