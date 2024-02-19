package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
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
        return licenseMapper.toLicenseDto(licenseRepository.save
                (licenseValidation.createLicenseValidator(licenseMapper.toLicense(licenseDto))));
    }

    public LicenseDto updateLicense(Long id, LicenseDto licenseDto) {
        licenseValidation.findLicenseValidator(id);
        return licenseMapper.toLicenseDto(licenseRepository.save
                (licenseValidation.createLicenseValidator(licenseMapper.toLicense(licenseDto))));
    }

    public void deleteLicense(Long id) {
        licenseRepository.delete(licenseValidation.findLicenseValidator(id));
    }

    public List<LicenseDto> getAllLicenseByBank_detailsId(Long bank_details_id) {
        return licenseMapper.toListLicenseDto(licenseRepository.findAllLicenseByBankDetailsId(bank_details_id));
    }
}
