package com.bank.authorization.aspects;


import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuditAOP {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Around("com.bank.authorization.aspects.Pointcuts.save())")
    public Object AroundSaveUser(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object start;
        try {
            start = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        com.bank.authorization.entity.Audit audit = new com.bank.authorization.entity.Audit();
        audit.setEntityType(args[0].getClass().getSimpleName());
        audit.setOperationType(joinPoint.getSignature().getName());
        for (Object object : args) {
            if (object instanceof UserDTO userDTO) {
                audit.setCreatedBy(userDTO.getProfileId().toString());
            }
        }
        audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        try {
            audit.setEntityJson(objectMapper.writeValueAsString(args[0]));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("AroundSaveUser: не удалось получить JSON у %s",
                    args[0].getClass().getSimpleName()));

        }
        auditService.saveAudit(audit);
        log.info("Создана запись в таблицу Audit");

        return start;
    }

    @Around("com.bank.authorization.aspects.Pointcuts.update()")
    public Object aroundUpdateUser(ProceedingJoinPoint proceedingJoinPoint) {

        Object[] args = proceedingJoinPoint.getArgs();
        Object object;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        com.bank.authorization.entity.Audit updateAudit = new com.bank.authorization.entity.Audit();
        updateAudit.setEntityType(args[0].getClass().getSimpleName());
        updateAudit.setOperationType(proceedingJoinPoint.getSignature().getName());
        updateAudit.setCreatedBy("User");
        for (Object obj : args) {
            if (obj instanceof UserDTO userDTO) {
                updateAudit.setModifiedBy(userDTO.getProfileId().toString());
            }
        }
        updateAudit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        updateAudit.setModifiedAt(new Timestamp(System.currentTimeMillis()));

        try {
            updateAudit.setEntityJson(objectMapper.writeValueAsString(args[0]));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("AroundUpdateUser: не удалось получить JSON у %s",
                    args[0].getClass().getSimpleName()));
        }
        auditService.saveAudit(updateAudit);
        log.info("Создана запись обновления в таблицу Audit");
        return object;
    }
}



