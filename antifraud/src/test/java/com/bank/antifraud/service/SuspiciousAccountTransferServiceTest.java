package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraud_predictor.AccountTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.util.TransferMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SuspiciousAccountTransferServiceTest {
    private final TransferMock transferMock = new TransferMock();
    private final SuspiciousAccountTransfers suspiciousTransfer = new SuspiciousAccountTransfers();
    private final SuspiciousAccountTransferRepository suspiciousTransferRepository = mock(SuspiciousAccountTransferRepository.class);
    private final AccountTransferFraudPredictor accountTransferFraudPredictor = mock(AccountTransferFraudPredictor.class);
    private final SuspiciousAccountTransferService suspiciousAccountTransferService = new SuspiciousAccountTransferService(accountTransferFraudPredictor, suspiciousTransferRepository);

    @Test
    public void test_saves_suspicious_transfer_with_valid_input() {
        // Arrange
        transferMock.setId(1);
        transferMock.setIdentificationNumber(123456789);
        transferMock.setAmount(50000);
        transferMock.setPurpose("Payment");
        transferMock.setAccountDetailId(1001);
        // Act
        SuspiciousAccountTransfers result = suspiciousAccountTransferService.saveSuspiciousTransfer(transferMock);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getAccountTransferId());
        assertFalse(result.isBlocked());
        assertFalse(result.isSuspicious());
        assertEquals("Everything is fine", result.getSuspiciousReason());

        verify(suspiciousTransferRepository, times(1)).save(any(SuspiciousAccountTransfers.class));
    }

    @Test
    public void test_throws_exception_when_transferMock_is_null() {
        // Arrange
        TransferMock transferMock = null;
        // Act & Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousAccountTransferService.saveSuspiciousTransfer(transferMock);
        });

        verify(suspiciousTransferRepository, never()).save(any(SuspiciousAccountTransfers.class));
    }

    @Test
    public void test_returnsSuspiciousAccountTransfersObjectIfExists() {
        // Arrange
        Long id = 1L;
        suspiciousTransfer.setId(id);
        when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.of(suspiciousTransfer));

        // Act
        SuspiciousAccountTransfers actualTransfer = suspiciousAccountTransferService.findSuspiciousTransferById(id);

        // Assert
        assertEquals(suspiciousTransfer, actualTransfer);
    }

    @Test
    public void test_throwsSuspiciousTransferNotFoundExceptionIfObjectDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            // Act
            suspiciousAccountTransferService.findSuspiciousTransferById(id);
        });
    }

    @Test
    public void test_updateSuspiciousTransfer_validInput() {
        // Arrange
        transferMock.setId(1);
        transferMock.setSuspiciousReason("Suspicious Reason");


        suspiciousTransfer.setId(1L);
        suspiciousTransfer.setAccountTransferId(1);
        suspiciousTransfer.setSuspicious(true);
        suspiciousTransfer.setSuspiciousReason("Suspicious Reason");
        suspiciousTransfer.setBlocked(false);

        when(accountTransferFraudPredictor.predict(transferMock)).thenReturn(true);
        when(suspiciousTransferRepository.findById(1L)).thenReturn(Optional.of(suspiciousTransfer));

        // Act
        suspiciousAccountTransferService.updateSuspiciousTransfer(suspiciousTransfer);

        // Assert
        verify(suspiciousTransferRepository).save(suspiciousTransfer);
    }

    @Test
    public void test_updateSuspiciousTransfer_suspiciousTransferNotFound() {
        // Arrange
        suspiciousTransfer.setId(1L);
        suspiciousTransfer.setAccountTransferId(1);
        suspiciousTransfer.setSuspicious(true);
        suspiciousTransfer.setSuspiciousReason("Suspicious Reason");
        suspiciousTransfer.setBlocked(false);

        when(suspiciousTransferRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousAccountTransferService.updateSuspiciousTransfer(suspiciousTransfer);
        });
    }

    @Test
    public void test_deleteSuspiciousTransfer_validId() {
        // Arrange
        Long id = 1L;
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.of(suspiciousTransfer));
        // Act
        suspiciousAccountTransferService.deleteSuspiciousTransfer(id);
        // Assert
        Mockito.verify(suspiciousTransferRepository, Mockito.times(1)).delete(suspiciousTransfer);
    }

    @Test
    public void test_deleteSuspiciousTransfer_invalidId() {
        // Arrange
        Long id = 1L;
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousAccountTransferService.deleteSuspiciousTransfer(id);
        });
    }

}
