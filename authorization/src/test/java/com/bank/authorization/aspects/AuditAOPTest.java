package com.bank.authorization.aspects;



import com.bank.authorization.service.AuditService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditAOPTest {

    @Mock
    ObjectMapper objectMapper;
    @Mock
    AuditService auditService;

    @Mock
    ProceedingJoinPoint joinPoint;

    @Mock
    MethodSignature methodSignature;

    @InjectMocks
    AuditAOP auditAOP;


    @Test
    void aroundSaveUser() throws Throwable {
        Object[] args = {1L,1L};
        doReturn(args).when(joinPoint).getArgs();

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(joinPoint.getSignature().getName()).thenReturn("test");
        when(objectMapper.writeValueAsString(any())).thenReturn("test");
        auditAOP.AroundSaveUser(joinPoint);
        verify(joinPoint).proceed();
        verify(auditService).saveAudit(any());
    }

    @Test
    void aroundUpdateUser() throws Throwable {


        Object[] args = {1L,1L};
        doReturn(args).when(joinPoint).getArgs();


        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(joinPoint.getSignature().getName()).thenReturn("test");
        when(objectMapper.writeValueAsString(any())).thenReturn("test");
        auditAOP.aroundUpdateUser(joinPoint);
        verify(joinPoint).proceed();
        verify(auditService).saveAudit(any());
    }
}