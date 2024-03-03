package com.bank.antifraud.controller;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraudpredictor.CardTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.antifraud.util.TransferMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@EnableJpaRepositories
@Component

public class SusCardTransferControllerTest {
    @MockBean
    SuspiciousCardTransferService suspiciousCardTransferService;
    @MockBean
    CardTransferFraudPredictor cardTransferFraudPredictor;
    @MockBean
    SuspiciousCardTransferRepository suspiciousCardTransferRepository;
    @MockBean
    SusCardTransferController susCardTransferController;

    @Test
    public void test_null_id() {
        // Arrange
        Long id = null;
        SusCardTransferController susCardTransferController = new SusCardTransferController(suspiciousCardTransferService);
        when(suspiciousCardTransferService.findSuspiciousTransferById(id)).thenThrow(SuspiciousTransferNotFoundException.class);

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> susCardTransferController.findById(id));
    }

    @Test
    public void test_valid_id() {
        // Arrange
        SusCardTransferController susCardTransferController1 = new SusCardTransferController(suspiciousCardTransferService);
        Long id = 1L;
        SuspiciousCardTransfer expectedTransfer = new SuspiciousCardTransfer();
        when(suspiciousCardTransferService.findSuspiciousTransferById(id)).thenReturn(expectedTransfer);

        // Act
        SuspiciousCardTransfer actualTransfer = susCardTransferController1.findById(id);

        // Assert
        assertEquals(expectedTransfer, actualTransfer);
    }
    @Test
    public void test_handle_exceptions() {
        // Arrange
        SusCardTransferController susCardTransferController = new SusCardTransferController(suspiciousCardTransferService);
        Long id = 1L;
        when(suspiciousCardTransferService.findSuspiciousTransferById(id)).thenThrow(new SuspiciousTransferNotFoundException("Suspicious transfer not found"));

        // Act & Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> susCardTransferController.findById(id));
    }

    @Test
    public void test_valid_transfer() {
        // Arrange
        SusCardTransferController susCardTransferController1 = new SusCardTransferController(suspiciousCardTransferService);
        TransferMock transferMock = new TransferMock();
        transferMock.setId(1);
        transferMock.setIdentificationNumber(123456789);
        transferMock.setAmount(1000);
        transferMock.setPurpose("Test transfer");
        transferMock.setAccountDetailId(1234567890);
        transferMock.setSuspiciousReason("Suspicious transfer");

        SuspiciousCardTransfer expectedTransfer = new SuspiciousCardTransfer();
        expectedTransfer.setId(1L);
        expectedTransfer.setCardTransferId(1);
        expectedTransfer.setBlocked(false);
        expectedTransfer.setSuspicious(true);
        expectedTransfer.setSuspiciousReason("Suspicious transfer");

        when(suspiciousCardTransferService.saveSuspiciousTransfer(transferMock)).thenReturn(expectedTransfer);

        // Act
        SuspiciousCardTransfer result = susCardTransferController1.checkSuspiciousTransfer(transferMock);

        // Assert
        assertEquals(expectedTransfer, result);
    }
    @Test
    public void test_deleteSuspiciousTransfer_invalidId() {
        // Arrange
        Long id = 1L;
        doThrow(SuspiciousTransferNotFoundException.class).when(suspiciousCardTransferService).deleteSuspiciousTransfer(id);
        SusCardTransferController susCardTransferController = new SusCardTransferController(suspiciousCardTransferService);

        // Act & Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> susCardTransferController.deleteSuspiciousTransfer(id));
    }
    @Test
    public void test_updateValidSuspiciousTransfer() {
        // Arrange
        SuspiciousCardTransfer suspiciousCardTransfer = new SuspiciousCardTransfer();
        suspiciousCardTransfer.setId(1L);
        suspiciousCardTransfer.setCardTransferId(123);
        suspiciousCardTransfer.setBlocked(false);
        suspiciousCardTransfer.setSuspicious(true);
        suspiciousCardTransfer.setSuspiciousReason("Suspicious transfer");

        SuspiciousCardTransferService suspiciousCardTransferService = mock(SuspiciousCardTransferService.class);
        SusCardTransferController controller = new SusCardTransferController(suspiciousCardTransferService);

        // Act
        controller.updateSuspiciousTransfer(suspiciousCardTransfer);

        // Assert
        verify(suspiciousCardTransferService).updateSuspiciousTransfer(suspiciousCardTransfer);
    }
}
