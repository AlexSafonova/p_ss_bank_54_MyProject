package com.bank.antifraud.aop;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Aspect
@Slf4j

public class AuditAOP {
    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditAOP(AuditRepository auditRepository, ObjectMapper objectMapper) {
        this.auditRepository = auditRepository;
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(* com.bank.antifraud.controller.SusCardTransferController.checkSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusAccTransferController.checkSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusPhoneTransferController.checkSuspiciousTransfer(..))")
    public void pointcutAllSuspiciousTransfersSave() {

    }

    @Pointcut("execution(* com.bank.antifraud.controller.SusCardTransferController.updateSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusAccTransferController.updateSuspiciousTransfer(..)) ||"
            + "execution(* com.bank.antifraud.controller.SusPhoneTransferController.updateSuspiciousTransfer(..))")
    public void pointcutAllSuspiciousTransfersUpdate() {

    }

    @AfterReturning("pointcutAllSuspiciousTransfersSave()")
    public void saveAuditAdvice(JoinPoint pjp) throws Throwable {
        Audit audit = new Audit();
        Object[] args = pjp.getArgs();
        audit.setOperationType(pjp.getSignature().getName());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        audit.setCreatedAt(timestamp);
        audit.setEntityType(args[0].getClass().getName().split("\\.")[args[0].getClass().getName().split("\\.").length - 1]);
        audit.setCreatedBy((args[0].toString().split(","))[4].substring(1));
        audit.setEntityJson(objectMapper.writeValueAsString(args[0]));
        auditRepository.save(audit);
    }
    /* Подразумевается, что изменять теги трансферов с подозрительных или заблокированных
    на чистые может только специальная система безопасности либо менелжер вручную */

    @After("pointcutAllSuspiciousTransfersUpdate()")
    public void updateAuditAdvice(JoinPoint pjp) throws Throwable {
        Audit audit = new Audit();
        Object[] args = pjp.getArgs();
        audit.setOperationType(pjp.getSignature().getName());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        audit.setCreatedAt(timestamp);
        audit.setEntityType(args[0].getClass().getName().split("\\.")[args[0].getClass().getName().split("\\.").length - 1]);
        audit.setCreatedBy("security manager");
        audit.setEntityJson(objectMapper.writeValueAsString(args[0]));
        auditRepository.save(audit);
    }
}