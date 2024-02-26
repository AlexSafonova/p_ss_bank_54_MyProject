package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Bank_details;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.Bank_detailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankDetailsValidationTest {

    @Mock
    private Bank_detailsRepository bankDetailsRepository;

    @InjectMocks
    private BankDetailsValidation bankDetailsValidation;

    @Mock
    ValidatorException validatorException;
    @Mock
    NotFoundException notFoundException;

    @Test
    public void testCreateBankDetailsValidator_ValidBankDetails() {
        Bank_details validBankDetails = new Bank_details();
        validBankDetails.setCity("New York");
        validBankDetails.setJoint_stock_company("Company Inc.");
        validBankDetails.setName("Bank ABC");
        validBankDetails.setCor_account(1234567890);
        validBankDetails.setInn(123456789L);
        validBankDetails.setKpp(123L);
        validBankDetails.setBik(987654321L);

        Bank_details result = bankDetailsValidation.createBankDetailsValidator(validBankDetails);

        assertNotNull(result);
        assertEquals(validBankDetails, result);
    }

    @Test
    public void testCreateBankDetailsValidator_NullBankDetails() {
        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(null));
    }

    @Test
    public void testCreateBankDetailsValidator_NullCity() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("");
        invalidBankDetails.setJoint_stock_company("Company Inc.");
        invalidBankDetails.setName("Bank ABC");
        invalidBankDetails.setCor_account(1234567890);
        invalidBankDetails.setInn(123456789L);
        invalidBankDetails.setKpp(123L);
        invalidBankDetails.setBik(987654321L);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testCreateBankDetailsValidator_NullJSC() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("ss");
        invalidBankDetails.setJoint_stock_company("");
        invalidBankDetails.setName("Bank ABC");
        invalidBankDetails.setCor_account(1234567890);
        invalidBankDetails.setInn(123456789L);
        invalidBankDetails.setKpp(123L);
        invalidBankDetails.setBik(987654321L);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testCreateBankDetailsValidator_NullName() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("ss");
        invalidBankDetails.setJoint_stock_company("dd");
        invalidBankDetails.setName("");
        invalidBankDetails.setCor_account(1234567890);
        invalidBankDetails.setInn(123456789L);
        invalidBankDetails.setKpp(123L);
        invalidBankDetails.setBik(987654321L);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testCreateBankDetailsValidator_NullCorAccount() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("ss");
        invalidBankDetails.setJoint_stock_company("dd");
        invalidBankDetails.setName("dd");
        invalidBankDetails.setCor_account(null);
        invalidBankDetails.setInn(123456789L);
        invalidBankDetails.setKpp(123L);
        invalidBankDetails.setBik(987654321L);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testCreateBankDetailsValidator_NullInn() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("ss");
        invalidBankDetails.setJoint_stock_company("dd");
        invalidBankDetails.setName("dd");
        invalidBankDetails.setCor_account(1111);
        invalidBankDetails.setInn(null);
        invalidBankDetails.setKpp(123L);
        invalidBankDetails.setBik(987654321L);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testCreateBankDetailsValidator_NullKpp() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("ss");
        invalidBankDetails.setJoint_stock_company("dd");
        invalidBankDetails.setName("dd");
        invalidBankDetails.setCor_account(1111);
        invalidBankDetails.setInn(111L);
        invalidBankDetails.setKpp(null);
        invalidBankDetails.setBik(987654321L);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testCreateBankDetailsValidator_NullBik() {
        Bank_details invalidBankDetails = new Bank_details();
        invalidBankDetails.setId(1L);
        invalidBankDetails.setCity("ss");
        invalidBankDetails.setJoint_stock_company("dd");
        invalidBankDetails.setName("dd");
        invalidBankDetails.setCor_account(1111);
        invalidBankDetails.setInn(111L);
        invalidBankDetails.setKpp(111L);
        invalidBankDetails.setBik(null);

        assertThrows(ValidatorException.class, () -> bankDetailsValidation.createBankDetailsValidator(invalidBankDetails));
    }

    @Test
    public void testFindBankDetailsValidator_ExistingId() {
        Long id = 1L;
        Bank_details bankDetails = new Bank_details();
        bankDetails.setId(id);

        when(bankDetailsRepository.findById(id)).thenReturn(java.util.Optional.of(bankDetails));

        Bank_details result = bankDetailsValidation.findBankDetailsValidator(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void testFindBankDetailsValidator_NonExistingId() {
        Long id = 999L;

        when(bankDetailsRepository.findById(id)).thenReturn(java.util.Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bankDetailsValidation.findBankDetailsValidator(id));

        assertEquals("Bank_details " + id + " not found", exception.getMessage());
    }

    @Test
    public void testCreateBankDetailsValidator_AllConditionsMet() {
        Bank_details validBankDetails = new Bank_details();
        validBankDetails.setCity("New York");
        validBankDetails.setJoint_stock_company("Company Inc.");
        validBankDetails.setName("Bank ABC");
        validBankDetails.setCor_account(1234567890);
        validBankDetails.setInn(123456789L);
        validBankDetails.setKpp(123L);
        validBankDetails.setBik(987654321L);

        Bank_details result = bankDetailsValidation.createBankDetailsValidator(validBankDetails);

        assertNotNull(result);
        assertEquals(validBankDetails, result);
    }
}
