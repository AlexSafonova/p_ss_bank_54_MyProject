package com.bank.antifraud.fraudpredictor;

import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.TransferMock;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//метод считает сколько раз был выполнен перевод средств в течение последних 60 минут и при превышении
//количества операций больше чем maxOperationsPerHour возвращает true(перевод подозрительный);
//также учитывается сумма перевода, при превышении maxAmount возвращает true(перевод подозрительный).
@Component
@RequiredArgsConstructor
@Setter
public class AccountTransferFraudPredictor implements Predictor {
    private final AuditRepository auditRepository;
    @Value("${fraud.max_Amount}")
    private Long maxAmount;
    @Value("${fraud.max_Operations_Per_Hour}")
    private int maxOperationsPerHour;

    @Override
    public boolean predict(TransferMock transferMock) {
        if (transferMock.getAmount() > maxAmount) {
            transferMock.setSuspiciousReason("Amount is too high");
            return true;
        } else if (auditRepository.findAllByAccountDetailId("accountDetailId=" + transferMock.getAccountDetailId())
                .stream().filter(audit -> audit.getCreatedAt()
                        .after(new Timestamp(System.currentTimeMillis() - 60 * 60 * 1000)))
                .count() > maxOperationsPerHour) {
            transferMock.setSuspiciousReason("Too many operations in the last hour");
            return true;
        }
        return false;
    }
}