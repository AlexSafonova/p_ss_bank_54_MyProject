package com.bank.antifraud.fraud_predictor;

import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.TransferMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//метод считает сколько раз был выполнен перевод средств в течение последних 60 минут и при превышении
//количества операций больше чем maxOperationsPerHour возвращает true(перевод подозрительный);
//также учитывается сумма перевода, при превышении maxAmount возвращает true(перевод подозрительный).
@Component
public class CardTransferFraudPredictor implements Predictor {
    private final Long maxAmount = 100000L;
    private final int maxOperationsPerHour = 5;
    private final AuditRepository auditRepository;

    @Autowired
    public CardTransferFraudPredictor(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public boolean predict(TransferMock transferMock) {
        if (transferMock.getAmount() > maxAmount) {
            transferMock.setSuspiciousReason("Amount is too high");
            return true;
        }
        if (auditRepository.findAllByAccountDetailId("accountDetailId=" + transferMock.getAccountDetailId())
                .stream().filter(audit -> audit.getCreatedAt()
                        .after(new Timestamp(System.currentTimeMillis() - 60 * 60 * 1000)))
                .count() > maxOperationsPerHour) {
            transferMock.setSuspiciousReason("Too many operations");
            return true;
        }
        return false;
    }
}