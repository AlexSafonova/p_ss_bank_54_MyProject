package com.bank.antifraud.controller;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraud_predictor.PhoneTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@EnableJpaRepositories
@Component

public class SusPhoneTransferControllerTest {
    @MockBean
    SuspiciousPhoneTransferService suspiciousPhoneTransferService;
    @MockBean
    PhoneTransferFraudPredictor phoneTransferFraudPredictor;
    @MockBean
    SuspiciousPhoneTransferRepository suspiciousPhoneTransferRepository;
    @Test
    public void test_retrieve_suspicious_phone_transfer() {
        // Arrange
        SusPhoneTransferController susPhoneTransferController = new SusPhoneTransferController(suspiciousPhoneTransferService);
        Long id = 1L;
        SuspiciousPhoneTransfers expectedTransfer = new SuspiciousPhoneTransfers();
        expectedTransfer.setId(id);
        when(suspiciousPhoneTransferService.findSuspiciousTransferById(id)).thenReturn(expectedTransfer);

        // Act
        SuspiciousPhoneTransfers actualTransfer = susPhoneTransferController.findById(id);

        // Assert
        assertEquals(expectedTransfer, actualTransfer);
        verify(suspiciousPhoneTransferService).findSuspiciousTransferById(id);
    }

    @Test
    public void test_null_id() {
        // Arrange
        SusPhoneTransferController susPhoneTransferController = new SusPhoneTransferController(suspiciousPhoneTransferService);
        Long id = null;

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            susPhoneTransferController.findById(id);
        });
        verify(suspiciousPhoneTransferService).findSuspiciousTransferById(id);
    }

    @Test
    public void test_deleteSuspiciousTransfer_validId() {
        // Arrange
        Long id = 1L;
        SusPhoneTransferController controller = new SusPhoneTransferController(suspiciousPhoneTransferService);

        // Act
        controller.deleteSuspiciousTransfer(id);

        // Assert
        verify(suspiciousPhoneTransferService, times(1)).deleteSuspiciousTransfer(id);
    }

    @Test
    public void test_deleteSuspiciousTransfer_invalidId() {
        // Arrange
        Long id = 1L;
        doThrow(SuspiciousTransferNotFoundException.class).when(suspiciousPhoneTransferService).deleteSuspiciousTransfer(id);
        SusPhoneTransferController controller = new SusPhoneTransferController(suspiciousPhoneTransferService);

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> controller.deleteSuspiciousTransfer(id));
    }

    @Test
    public void test_throwExceptionWhenInputIsNull() {
        // Arrange
        SuspiciousPhoneTransferService suspiciousPhoneTransferService = new SuspiciousPhoneTransferService(phoneTransferFraudPredictor, suspiciousPhoneTransferRepository);
        SusPhoneTransferController susPhoneTransferController = new SusPhoneTransferController(suspiciousPhoneTransferService);
        SuspiciousPhoneTransfers suspiciousPhoneTransfer = null;

        // Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            // Act
            susPhoneTransferController.updateSuspiciousTransfer(suspiciousPhoneTransfer);
        });
    }

    @Test
    public void test_valid_transfer() {
        // Arrange
        SusPhoneTransferController controller = new SusPhoneTransferController(suspiciousPhoneTransferService);
        TransferMock transferMock = new TransferMock();
        transferMock.setId(1);
        transferMock.setIdentificationNumber(123456789);
        transferMock.setAmount(1000);
        transferMock.setPurpose("Test transfer");
        transferMock.setAccountDetailId(12345);

        SuspiciousPhoneTransfers expected = new SuspiciousPhoneTransfers();
        expected.setId(1L);
        expected.setPhoneTransferId(1);
        expected.setBlocked(false);
        expected.setSuspicious(true);
        expected.setSuspiciousReason("Suspicious reason");

        when(suspiciousPhoneTransferService.saveSuspiciousTransfer(transferMock)).thenReturn(expected);

        // Act
        SuspiciousPhoneTransfers result = controller.checkSuspiciousTransfer(transferMock);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void test_invalid_identification_number() {
        // Arrange
        SusPhoneTransferController controller = new SusPhoneTransferController(suspiciousPhoneTransferService);
        TransferMock transferMock = new TransferMock();
        transferMock.setId(1);
        transferMock.setIdentificationNumber(0);
        transferMock.setAmount(1000);
        transferMock.setPurpose("Test transfer");
        transferMock.setAccountDetailId(12345);

        when(suspiciousPhoneTransferService.saveSuspiciousTransfer(transferMock)).thenThrow(new SuspiciousTransferNotFoundException("Transfer is null"));

        // Act and Assert
        assertThrows(SuspiciousTransferNotFoundException.class, () -> {
            controller.checkSuspiciousTransfer(transferMock);
        });
    }
}
