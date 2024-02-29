package com.bank.antifraud.fraud_predictor;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.TransferMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PhoneTransferFraudPredictorTest {
    private final TransferMock transferMock = new TransferMock();
    private final AuditRepository auditRepository = Mockito.mock(AuditRepository.class);
    private final PhoneTransferFraudPredictor predictor = new PhoneTransferFraudPredictor(auditRepository);

    @Test
    public void test_transfer_amount_less_than_maxAmount() {
        //Arrange
        transferMock.setAmount(50000);
        //Act
        boolean result = predictor.predict(transferMock);
        //Assert
        assertFalse(result);
    }

    @Test
    public void test_transfer_amount_greater_than_maxAmount() {
        //Arrange
        transferMock.setAmount(150000);
        //Act
        boolean result = predictor.predict(transferMock);
        //Assert
        assertTrue(result);
    }

    @Test
    public void test_number_of_operations_greater_than_maxOperationsPerHour() {
        //Arrange
        transferMock.setAccountDetailId(123);
        List<Audit> audits = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Audit audit = new Audit();
            audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            audits.add(audit);
        }
        //Arrange and Act
        when(auditRepository.findAllByAccountDetailId("accountDetailId=123")).thenReturn(audits);
        boolean result = predictor.predict(transferMock);
        //Assert
        assertTrue(result);
    }

    @Test
    public void test_number_of_operations_less_than_maxOperationsPerHour() {
        //Arrange
        transferMock.setAccountDetailId(123);
        List<Audit> audits = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Audit audit = new Audit();
            audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            audits.add(audit);
        }
        //Arrange and Act
        when(auditRepository.findAllByAccountDetailId("accountDetailId=123")).thenReturn(audits);
        boolean result = predictor.predict(transferMock);
        //Assert
        assertFalse(result);
    }
}
