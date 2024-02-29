package com.bank.antifraud.aop;

import com.bank.antifraud.service.AuditService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class AuditAOPTest {
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
    public void test_save_audit() throws Throwable {
        Object[] args = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi, ut aliquip ex ea commodo consequat.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris, nisi ut aliquip ex ea commodo consequat."};
        doReturn(args).when(joinPoint).getArgs();
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(objectMapper.writeValueAsString(any())).thenReturn("test");
        auditAOP.saveAuditAdvice(joinPoint);
        verify(joinPoint).getArgs();
        verify(joinPoint).getSignature();
        verify(auditService, times(1)).saveAudit(any());
    }

    @Test
    public void test_update_audit() throws Throwable {
        Object[] args = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi, ut aliquip ex ea commodo consequat.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris, nisi ut aliquip ex ea commodo consequat."};
        doReturn(args).when(joinPoint).getArgs();
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(objectMapper.writeValueAsString(any())).thenReturn("test");
        auditAOP.updateAuditAdvice(joinPoint);
        verify(joinPoint).getArgs();
        verify(joinPoint).getSignature();
        verify(auditService, times(1)).saveAudit(any());
    }

}
