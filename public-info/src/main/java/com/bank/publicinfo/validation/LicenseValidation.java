package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.LicenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LicenseValidation {
    private final LicenseRepository licenseRepository;

    public License createLicenseValidator(License license) {
        if (license.getPhoto() != null) {
            return license;
        } else {
            throw new ValidatorException("Empty fields in license");
        }
    }

    public License findLicenseValidator(Long id) {
        if (licenseRepository.findById(id).isPresent()) {
            return licenseRepository.findById(id).get();
        } else {
            throw new NotFoundException("License " + id + " not found");
        }
    }
}
