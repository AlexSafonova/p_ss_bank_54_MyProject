package com.bank.antifraud.controller;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraud_predictor.AccountTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@EnableJpaRepositories
@Component
public class SusAccTransferControllerTest {
    @MockBean
    SuspiciousAccountTransferService suspiciousAccountTransferService;
    @MockBean
    AccountTransferFraudPredictor accountTransferFraudPredictor;
    @MockBean
    SuspiciousAccountTransferRepository suspiciousAccountTransferRepository;

    @Test
    public void test_valid_id() {
        // Arrange
        SusAccTransferController susAccTransferController = new SusAccTransferController(suspiciousAccountTransferService);
        Long id = 1L;
        SuspiciousAccountTransfers expected = new SuspiciousAccountTransfers();
        when(suspiciousAccountTransferService.findSuspiciousTransferById(id)).thenReturn(expected);

        // Act
        SuspiciousAccountTransfers result = susAccTransferController.findById(id);

        // Assert
        assertEquals(expected, result);
        verify(suspiciousAccountTransferService, times(1)).findSuspiciousTransferById(id);
    }

    @Test
    public void test_invalid_id() {
        // Arrange
        SusAccTransferController susAccTransferController = new SusAccTransferController(suspiciousAccountTransferService);
        Long id = 2L;
        when(suspiciousAccountTransferService.findSuspiciousTransferById(id)).thenReturn(null);

        // Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            // Act
            susAccTransferController.findById(id);
        });
        verify(suspiciousAccountTransferService, times(1)).findSuspiciousTransferById(id);
    }

    @Test
    public void test_validTransferMockObject_returnsSuspiciousAccountTransfers() {
        // Arrange
        SuspiciousAccountTransferService suspiciousAccountTransferService = new SuspiciousAccountTransferService(accountTransferFraudPredictor, suspiciousAccountTransferRepository);
        SusAccTransferController susAccTransferController = new SusAccTransferController(suspiciousAccountTransferService);
        TransferMock transferMock = new TransferMock();
        transferMock.setId(1);
        transferMock.setIdentificationNumber(123456789);
        transferMock.setAmount(50000);
        transferMock.setPurpose("Payment");
        transferMock.setAccountDetailId(1001);

        SuspiciousAccountTransfers expected = new SuspiciousAccountTransfers();
        expected.setBlocked(false);
        expected.setAccountTransferId(1);
        expected.setSuspicious(false);
        expected.setSuspiciousReason("Everything is fine");

        // Act
        SuspiciousAccountTransfers result = susAccTransferController.checkSuspiciousTransfer(transferMock);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void test_nullTransferMockObject_throwsSuspiciousTransferNotFoundException() {
        // Arrange
        SuspiciousAccountTransferService suspiciousAccountTransferService = new SuspiciousAccountTransferService(accountTransferFraudPredictor, suspiciousAccountTransferRepository);
        TransferMock transferMock = null;
        SusAccTransferController susAccTransferController = new SusAccTransferController(suspiciousAccountTransferService);

        // Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            // Act
            susAccTransferController.checkSuspiciousTransfer(transferMock);
        });
    }
}
