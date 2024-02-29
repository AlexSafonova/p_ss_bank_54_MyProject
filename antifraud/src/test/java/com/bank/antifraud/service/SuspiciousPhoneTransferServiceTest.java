package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraud_predictor.PhoneTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.util.TransferMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class SuspiciousPhoneTransferServiceTest {
    private final TransferMock transferMock = new TransferMock();
    private final SuspiciousPhoneTransfers suspiciousPhoneTransfers = new SuspiciousPhoneTransfers();
    private final SuspiciousPhoneTransferRepository suspiciousTransferRepository = mock(SuspiciousPhoneTransferRepository.class);
    private final PhoneTransferFraudPredictor phoneTransferFraudPredictor = mock(PhoneTransferFraudPredictor.class);
    private final SuspiciousPhoneTransferService suspiciousPhoneTransferService = new SuspiciousPhoneTransferService(phoneTransferFraudPredictor, suspiciousTransferRepository);

    @Test
    public void test_saves_suspicious_transfer_with_valid_input() {
        // Arrange
        transferMock.setId(1);
        transferMock.setIdentificationNumber(123456789);
        transferMock.setAmount(50000);
        transferMock.setPurpose("Payment");
        transferMock.setAccountDetailId(1234567890);
        // Act
        SuspiciousPhoneTransfers result = suspiciousPhoneTransferService.saveSuspiciousTransfer(transferMock);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPhoneTransferId());
        assertFalse(result.isBlocked());
        assertFalse(result.isSuspicious());
        assertEquals("Everything is fine", result.getSuspiciousReason());
    }

    @Test
    public void test_throws_exception_when_transferMock_is_null() {
        // Arrange
        TransferMock transferMock = null;

        // Act & Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousPhoneTransferService.saveSuspiciousTransfer(transferMock);
        });
    }

    @Test
    public void test_returns_correct_object_with_valid_id() {
        // Arrange
        Long id = 1L;
        suspiciousPhoneTransfers.setId(id);
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.of(suspiciousPhoneTransfers));

        // Act
        SuspiciousPhoneTransfers actualTransfer = suspiciousPhoneTransferService.findSuspiciousTransferById(id);

        // Assert
        assertEquals(suspiciousPhoneTransfers, actualTransfer);
    }

    @Test
    public void test_throws_exception_with_null_id() {
        // Arrange
        Long id = null;

        // Act & Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousPhoneTransferService.findSuspiciousTransferById(id);
        });
    }

    @Test
    public void test_updateSuspiciousTransfer_validData() {
        // Arrange
        transferMock.setId(1);
        transferMock.setSuspiciousReason("Suspicious Reason");
        suspiciousPhoneTransfers.setId(1L);
        suspiciousPhoneTransfers.setPhoneTransferId(1);
        suspiciousPhoneTransfers.setSuspicious(true);
        suspiciousPhoneTransfers.setSuspiciousReason("Suspicious Reason");
        suspiciousPhoneTransfers.setBlocked(false);

        Mockito.when(phoneTransferFraudPredictor.predict(transferMock)).thenReturn(true);
        Mockito.when(suspiciousTransferRepository.findById(1L)).thenReturn(Optional.of(suspiciousPhoneTransfers));

        // Act
        suspiciousPhoneTransferService.updateSuspiciousTransfer(suspiciousPhoneTransfers);

        // Assert
        Mockito.verify(suspiciousTransferRepository).save(suspiciousPhoneTransfers);
    }

    @Test
    public void test_updateSuspiciousTransfer_nullInput() {
        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousPhoneTransferService.updateSuspiciousTransfer(null);
        });
    }

    @Test
    public void test_deleteSuspiciousTransfer_validId() {
        // Arrange
        Long id = 1L;
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.of(suspiciousPhoneTransfers));
        // Act
        suspiciousPhoneTransferService.deleteSuspiciousTransfer(id);

        // Assert
        Mockito.verify(suspiciousTransferRepository).delete(suspiciousPhoneTransfers);
    }

    @Test
    public void test_deleteSuspiciousTransfer_invalidId() {
        // Arrange
        Long id = 1L;
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousPhoneTransferService.deleteSuspiciousTransfer(id);
        });
    }
}
