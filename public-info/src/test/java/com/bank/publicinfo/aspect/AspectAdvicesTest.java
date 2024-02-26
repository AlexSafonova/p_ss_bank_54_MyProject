package com.bank.publicinfo.aspect;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AspectAdvicesTest {
    @InjectMocks
    AspectAdvices aspectAdvices;
    @Mock
    AuditService auditService;
    @Mock
    ProceedingJoinPoint pjp;
    private final String PROFILE_ID = "profile_id";

    @Mock
    MethodSignature methodSignature;

    Audit audit1 = new Audit(1L, "Atm", "getAllAtm", "profile_id", "", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "111", "111");


    @Test
    void aroundAllGetAllMetodsAdvice() throws Throwable {
        when(pjp.getSignature()).thenReturn(methodSignature);
        when(pjp.getSignature().getName()).thenReturn("operation");
        when(methodSignature.getDeclaringTypeName()).thenReturn("tttttttttttttttttttttttttttttEntitytttttttttt");
        when(pjp.proceed()).thenReturn("text");

        aspectAdvices.aroundAllGetAllMetodsAdvice(pjp);

        verify(pjp).proceed();
        verify(auditService).addAudit(any());


    }

    @Test
    void afterAllGetAllMetodsAdvice() {
        aspectAdvices.afterAllGetAllMetodsAdvice();
    }
}