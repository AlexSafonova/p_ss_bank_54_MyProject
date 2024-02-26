package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.AtmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AtmValidationTest {
    @InjectMocks
    AtmValidation atmValidation;
    @Mock
    AtmRepository atmRepository;
    @Mock
    ValidatorException validatorException;
    @Mock
    Atm atm;

    Atm atm1 = new Atm(1L, "address", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:59+03:00"), true, null);

    @Test
    public void testCreateAtmValidator() {
        assertDoesNotThrow(() -> atmValidation.createAtmValidator(atm1));

        assertThrows(ValidatorException.class, () -> atmValidation.createAtmValidator(null));
    }

    @Test
    void createAtmValidatorGetException() {
        assertThrows(ValidatorException.class, () -> atmValidation.createAtmValidator(null));
    }

    @Test
    void createAtmValidatorGetExceptionNullAddress() {
        Atm invalidAtm = new Atm();
        invalidAtm.setAddress("");
        invalidAtm.setId(1L);
        invalidAtm.setAll_hours(true);
        invalidAtm.setStart_of_work(OffsetTime.parse("00:00:00+03:00"));
        invalidAtm.setEnd_of_work(OffsetTime.parse("00:00:00+03:00"));
        invalidAtm.setBranch(null);

        assertThrows(ValidatorException.class, () -> atmValidation.createAtmValidator(invalidAtm));
    }

    @Test
    public void testCreateAtmValidator_AllhoursNull() {
        Atm invalidAtm = new Atm();
        invalidAtm.setAddress("null");
        invalidAtm.setId(1L);
        invalidAtm.setAll_hours(null);
        invalidAtm.setStart_of_work(OffsetTime.parse("00:00:00+03:00"));
        invalidAtm.setEnd_of_work(OffsetTime.parse("00:00:00+03:00"));
        invalidAtm.setBranch(null);

        assertThrows(ValidatorException.class, () -> atmValidation.createAtmValidator(invalidAtm));
    }


    @Test
    void findAtmValidator() {
        Optional<Atm> atm2 = Optional.ofNullable(atm1);
        doReturn(atm2).when(atmRepository).findById(1L);
        var result = atmValidation.findAtmValidator(1L);

        assertNotNull(result);
        assertEquals(atm1, result);
    }

    @Test
    void findAtmValidatorGetException() {
        Optional<Atm> atm2 = Optional.empty();
        doReturn(atm2).when(atmRepository).findById(1L);
        assertThrows(NotFoundException.class, () -> atmValidation.findAtmValidator(1L));
    }
}

