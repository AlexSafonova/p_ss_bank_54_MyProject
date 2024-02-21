package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LicenseService {
    List<LicenseDto> getAllLicense();

    LicenseDto findLicenseById(Long id);

    LicenseDto addLicense(LicenseDto licenseDto);

    LicenseDto updateLicense(Long id, LicenseDto licenseDto);

    void deleteLicense(Long id);

    List<LicenseDto> getAllLicenseByBank_detailsId(Long bank_details_id);
}
