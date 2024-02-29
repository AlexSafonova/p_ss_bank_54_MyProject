package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraud_predictor.CardTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.util.TransferMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SuspiciousCardTransferServiceTest {

    private final TransferMock transferMock = new TransferMock();
    private final SuspiciousCardTransfer suspiciousTransfer = new SuspiciousCardTransfer();
    private final SuspiciousCardTransferRepository suspiciousTransferRepository = mock(SuspiciousCardTransferRepository.class);
    private final CardTransferFraudPredictor cardTransferFraudPredictor = mock(CardTransferFraudPredictor.class);
    private final SuspiciousCardTransferService suspiciousCardTransferService = new SuspiciousCardTransferService(cardTransferFraudPredictor, suspiciousTransferRepository);

    @Test
    public void test_saves_suspicious_transfer_with_valid_input() {
        // Arrange
        transferMock.setId(1);
        transferMock.setIdentificationNumber(123456789);
        transferMock.setAmount(50000);
        transferMock.setPurpose("Payment");
        transferMock.setAccountDetailId(1001);
        transferMock.setSuspiciousReason("Everything is fine");

        suspiciousTransfer.setCardTransferId(transferMock.getId());
        suspiciousTransfer.setBlocked(false);
        suspiciousTransfer.setSuspicious(false);
        suspiciousTransfer.setSuspiciousReason("Everything is fine");

        when(cardTransferFraudPredictor.predict(transferMock)).thenReturn(false);
        when(suspiciousTransferRepository.save(Mockito.any(SuspiciousCardTransfer.class))).thenReturn(suspiciousTransfer);

        // Act
        SuspiciousCardTransfer result = suspiciousCardTransferService.saveSuspiciousTransfer(transferMock);

        // Assert
        verify(cardTransferFraudPredictor).predict(transferMock);
        verify(suspiciousTransferRepository).save(suspiciousTransfer);
        assertEquals(suspiciousTransfer, result);
    }

    @Test
    public void test_throws_exception_if_transfermock_is_null() {
        // Arrange
        TransferMock transferMock = null;

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousCardTransferService.saveSuspiciousTransfer(transferMock);
        });
    }

    @Test
    public void test_returns_suspicious_card_transfer() {
        // Arrange
        Long id = 1L;
        when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.of(suspiciousTransfer));

        // Act
        SuspiciousCardTransfer actualTransfer = suspiciousCardTransferService.findSuspiciousTransferById(id);

        // Assert
        assertEquals(suspiciousTransfer, actualTransfer);
    }

    @Test
    public void test_throwsSuspiciousTransferNotFoundException_whenIdDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            // Act
            suspiciousCardTransferService.findSuspiciousTransferById(id);
        });
    }

    @Test
    public void test_updateSuspiciousTransfer_validInput() {
        // Arrange
        suspiciousTransfer.setId(1L);
        suspiciousTransfer.setCardTransferId(123456);
        suspiciousTransfer.setSuspicious(true);
        suspiciousTransfer.setSuspiciousReason("Fraudulent activity detected");
        suspiciousTransfer.setBlocked(true);
        suspiciousTransfer.setBlockReason("High risk transaction");

        when(suspiciousTransferRepository.findById(1L)).thenReturn(Optional.of(suspiciousTransfer));
        // Act
        suspiciousCardTransferService.updateSuspiciousTransfer(suspiciousTransfer);

        // Assert
        verify(suspiciousTransferRepository, times(1)).save(suspiciousTransfer);
    }

    @Test
    public void test_updateSuspiciousTransfer_nullInput() {
        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousCardTransferService.updateSuspiciousTransfer(null);
        });
    }

    @Test
    public void test_deleteSuspiciousTransfer_validId() {
        // Arrange
        Long id = 1L;
        suspiciousTransfer.setId(id);
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.of(suspiciousTransfer));

        // Act
        suspiciousCardTransferService.deleteSuspiciousTransfer(id);

        // Assert
        Mockito.verify(suspiciousTransferRepository).delete(suspiciousTransfer);
    }

    @Test
    public void test_deleteSuspiciousTransfer_invalidId() {
        // Arrange
        Long id = 1L;
        Mockito.when(suspiciousTransferRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            suspiciousCardTransferService.deleteSuspiciousTransfer(id);
        });
    }
}
