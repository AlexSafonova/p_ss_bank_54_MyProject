package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Bank_details;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.Bank_detailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BankDetailsValidation {
    private final Bank_detailsRepository bankDetailsRepository;
    public Bank_details createBankDetailsValidator (Bank_details bankDetails) {
        if (!bankDetails.getCity().isBlank()
                && !bankDetails.getJoint_stock_company().isBlank()
                && !bankDetails.getName().isBlank()
                && bankDetails.getBik().describeConstable().isPresent()
                && bankDetails.getCor_account() != null
                && bankDetails.getInn() != null
                && bankDetails.getKpp() != null
                && bankDetails.getBik() != null) {
            return bankDetails;
        }
        else {
            throw new ValidatorException("Empty fields in bank_details");
        }
    }
    public Bank_details findBankDetailsValidator (Long id) {
        if(bankDetailsRepository.findById(id).isPresent()) {
            return bankDetailsRepository.findById(id).get();
        }
        else {
            throw new NotFoundException("Bank_details " + id + " not found");
        }
    }
}
