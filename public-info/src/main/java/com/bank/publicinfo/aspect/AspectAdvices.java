package com.bank.publicinfo.aspect;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.service.AuditService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import static java.rmi.server.LogStream.log;


@Aspect
@Component
@AllArgsConstructor
@Log4j2
public class AspectAdvices {

    private final AuditService auditService;

    @Around("com.bank.publicinfo.aspect.Pointcuts.allMethodsInServicesWithoutAudit()")
    public Object afterAllGetAllMetodsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Audit audit = new Audit();
        audit.setOperation_type(methodSignature.getMethod().getName());
        audit.setEntity_type(methodSignature.getDeclaringTypeName().substring(28,methodSignature.getDeclaringTypeName().length()-1));
        audit.setCreated_by("profile_id");
        audit.setCreated_at(new Timestamp(System.currentTimeMillis()));
        Object returnObject = (Object) joinPoint.proceed();
        audit.setEntity_json(returnObject.toString());
        audit.setNew_entity_json(returnObject.toString());
        audit.setModified_at(new Timestamp(System.currentTimeMillis()));
        audit.setModified_by("profile_id");
        auditService.addAudit(audit);
        return returnObject;
    }


}
