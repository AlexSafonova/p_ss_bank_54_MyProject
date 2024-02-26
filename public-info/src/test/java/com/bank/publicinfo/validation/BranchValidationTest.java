package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;

import java.time.OffsetTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BranchValidationTest {
    @InjectMocks
    BranchValidation branchValidation;
    @Mock
    BranchRepository branchRepository;
    Branch branch1 = new Branch(1L, "address1", 1234567L, "city1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));
    Branch branchAdr = new Branch(1L, "", 1234567L, "city1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));
    Branch branchCity = new Branch(1L, "address1", 1234567L, "", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));
    Branch branchEoW = new Branch(1L, "address1", 1234567L, "city1", OffsetTime.parse("00:00:00+03:00"), null);
    Branch branchSoW = new Branch(1L, "address1", 1234567L, "city1", null, OffsetTime.parse("23:59:00+03:00"));
    Branch branchPhone = new Branch(1L, "address1", null, "city1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));

    @Test
    void createBranchValidator() {
        var result = branchValidation.createBranchValidator(branch1);

        assertNotNull(result);
        assertEquals(branch1, result);
    }

    @Test
    void createBranchValidatorGetException() {
        try {
            assertThrows(ValidatorException.class, () -> branchValidation.createBranchValidator(null));
        } catch (AssertionFailedError e) {
        }
    }

    @Test
    void createBranchValidatorGetExceptionNullAddress() {
        assertThrows(ValidatorException.class, () -> branchValidation.createBranchValidator(branchAdr));
    }

    @Test
    void createBranchValidatorGetExceptionNullCity() {
        assertThrows(ValidatorException.class, () -> branchValidation.createBranchValidator(branchCity));
    }

    @Test
    void createBranchValidatorGetExceptionNullEndOfWork() {
        assertThrows(ValidatorException.class, () -> branchValidation.createBranchValidator(branchEoW));
    }

    @Test
    void createBranchValidatorGetExceptionNullPhone() {
        assertThrows(ValidatorException.class, () -> branchValidation.createBranchValidator(branchPhone));
    }

    @Test
    void createBranchValidatorGetExceptionNullStartOfWork() {
        assertThrows(ValidatorException.class, () -> branchValidation.createBranchValidator(branchSoW));
    }

    @Test
    void findBranchValidator() {
        Optional<Branch> branch = Optional.ofNullable(branch1);
        doReturn(branch).when(branchRepository).findById(1L);
        var result = branchValidation.findBranchValidator(1L);

        assertNotNull(result);
        assertEquals(branch1, result);
    }

    @Test
    void findBranchValidatorGetException() {
        Optional<Branch> branch2 = Optional.empty();
        doReturn(branch2).when(branchRepository).findById(1L);
        assertThrows(NotFoundException.class, () -> branchValidation.findBranchValidator(1L));
    }
}