package com.bank.publicinfo.aspect;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Aspect
@Component
public class AspectAdvices {

    public AspectAdvices(AuditService auditService) {
        this.auditService = auditService;
    }

    private final AuditService auditService;

    private final String PROFILE_ID = "profile_id";

    Audit audit = new Audit();

    @Around("com.bank.publicinfo.aspect.Pointcuts.allMethodsInServicesWithoutAudit()")
    public Object aroundAllGetAllMetodsAdvice(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        audit.setOperation_type(methodSignature.getMethod().getName());
        audit.setEntity_type(methodSignature.getDeclaringTypeName().substring(28, methodSignature.getDeclaringTypeName().length() - 11));
        audit.setCreated_by(PROFILE_ID);
        audit.setCreated_at(new Timestamp(System.currentTimeMillis()));
        Object returnObject = (Object) pjp.proceed();
        audit.setEntity_json(returnObject.toString());
        audit.setNew_entity_json(returnObject.toString());
        auditService.addAudit(audit);
        return returnObject;
    }

    @AfterReturning("com.bank.publicinfo.aspect.Pointcuts.allMethodsInServicesWithoutAudit()")
    public void afterAllGetAllMetodsAdvice() {
        audit.setModified_by(PROFILE_ID);
        audit.setModified_at(new Timestamp(System.currentTimeMillis()));
    }
}
