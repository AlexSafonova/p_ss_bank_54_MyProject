package com.bank.antifraud.aop;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.AfterAll;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Aspect
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    @After("execution(* com.bank.antifraud.controller.SusCardTransferController.checkSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusAccTransferController.checkSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusPhoneTransferController.checkSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusCardTransferController.updateSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusAccTransferController.updateSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusPhoneTransferController.updateSuspiciousTransfer(..))")
    public void newAuditAdvice(JoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Audit audit = Audit.builder()
        .operationType(pjp.getSignature().getName())
        .createdAt(timestamp)
        .entityType(args[0].getClass().getName().split("\\.")[args[0].getClass().getName().split("\\.").length - 1])
        .entityJson(objectMapper.writeValueAsString(args[0]))
        .createdBy((args[0].toString().split(","))[4].substring(1))
                .build();
        auditService.saveAudit(audit);
    }
}