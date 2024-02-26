package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.AtmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AtmValidation {
    private final AtmRepository atmRepository;

    public Atm createAtmValidator(Atm atm) {
        if (atm != null && !atm.getAddress().isBlank() && atm.getAll_hours() != null) {
            return atm;
        } else {
            throw new ValidatorException("Can't be null Atm or empty address and all_hours");
        }
    }

    public Atm findAtmValidator(Long id) {
        if (atmRepository.findById(id).isPresent()) {
            return atmRepository.findById(id).get();
        } else {
            throw new NotFoundException("Atm " + id + " not found");
        }
    }
}
