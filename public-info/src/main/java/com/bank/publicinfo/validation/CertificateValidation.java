package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CertificateValidation {
    private final CertificateRepository certificateRepository;

    public Certificate createCertificateValidator(Certificate certificate) {
        if (certificate != null && certificate.getPhoto() != null) {
            return certificate;
        } else {
            throw new ValidatorException("Empty Certificate or fields in certificate");
        }
    }

    public Certificate findCertificateValidator(Long id) {
        if (certificateRepository.findById(id).isPresent()) {
            return certificateRepository.findById(id).get();
        } else {
            throw new NotFoundException("Certificate " + id + " not found");
        }
    }
}
