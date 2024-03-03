package com.bank.antifraud.fraudpredictor;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.TransferMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AccountTransferFraudPredictorTest {
    private final TransferMock transferMock = new TransferMock();

    private final AuditRepository auditRepository = Mockito.mock(AuditRepository.class);
    private final AccountTransferFraudPredictor predictor = new AccountTransferFraudPredictor(auditRepository);

    @Test
    public void test_predict_returns_false_when_transferMock_amount_is_less_than_maxAmount() {
        // Arrange
        transferMock.setAmount(100);

        predictor.setMaxAmount(200L);

        // Act
        boolean result = predictor.predict(transferMock);

        // Assert
        assertFalse(result);
    }

    @Test
    public void test_predict_returns_false_when_transferMock_amount_is_equal_to_maxAmount() {
        // Arrange
        transferMock.setAmount(200);

        predictor.setMaxAmount(200L);

        // Act
        boolean result = predictor.predict(transferMock);

        // Assert
        assertFalse(result);
    }

    @Test
    public void test_predict_returns_true_when_transferMock_amount_is_greater_than_maxAmount() {
        // Arrange
        transferMock.setAmount(300);

        predictor.setMaxAmount(200L);

        // Act
        boolean result = predictor.predict(transferMock);

        // Assert
        assertTrue(result);
    }
    @Test
    public void test_predict_returns_false_when_number_of_operations_in_the_last_hour_is_less_than_or_equal_to_maxOperationsPerHour() {
        // Arrange
        transferMock.setAccountDetailId(123);

        when(auditRepository.findAllByAccountDetailId("accountDetailId=" + transferMock.getAccountDetailId()))
                .thenReturn(Collections.emptyList());
        predictor.setMaxAmount(100L);

        predictor.setMaxOperationsPerHour(5);

        // Act
        boolean result = predictor.predict(transferMock);

        // Assert
        assertFalse(result);
    }
    @Test
    public void test_predict_sets_suspiciousReason_to_null_when_number_of_operations_in_the_last_hour_is_less_than_or_equal_to_maxOperationsPerHour() {
        // Arrange
        transferMock.setAccountDetailId(123);
        Audit audit = Mockito.mock(Audit.class);
        when(auditRepository.findAllByAccountDetailId("accountDetailId=" + transferMock.getAccountDetailId()))
                .thenReturn(Collections.singletonList(audit));
        when(audit.getCreatedAt()).thenReturn(new Timestamp(System.currentTimeMillis() - 10 * 60 * 1000));

        predictor.setMaxOperationsPerHour(5);
        predictor.setMaxAmount(100L);

        // Act
        boolean result = predictor.predict(transferMock);

        // Assert
        assertNull(transferMock.getSuspiciousReason());
    }
    @Test
    public void test_predict_returns_true_when_number_of_operations_in_the_last_hour_is_greater_than_maxOperationsPerHour() {
        // Arrange
        transferMock.setAccountDetailId(123);
        List<Audit> audits = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Audit audit = Mockito.mock(Audit.class);
            when(audit.getCreatedAt()).thenReturn(new Timestamp(System.currentTimeMillis() - 10 * 60 * 1000));
            audits.add(audit);
        }
        when(auditRepository.findAllByAccountDetailId("accountDetailId=" + transferMock.getAccountDetailId()))
                .thenReturn(audits);
        predictor.setMaxOperationsPerHour(5);
        predictor.setMaxAmount(100L);
        // Act
        boolean result = predictor.predict(transferMock);
        // Assert
        assertTrue(result);
    }
}
