package com.bank.publicinfo.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
  /*  @Pointcut("execution(* create*(..))")
    public void allAddMethodInServices(){}*/

    @Pointcut("execution(* com.bank.publicinfo.service.*.*(..))")
    public void allMethodsInServices(){}

    @Pointcut("execution(* com.bank.publicinfo.service.AuditService.*(..))")
    public void allMethodsInAuditServices(){}

    @Pointcut("execution(* com.bank.publicinfo.service.*.*(..)) && !allMethodsInAuditServices() && !allMethodsInAuditServicesWithoutDelete()")
    public void allMethodsInServicesWithoutAudit(){}

    @Pointcut("execution(* com.bank.publicinfo.service.*.delete*(..))")
    public void allMethodsInAuditServicesWithoutDelete(){}

}
