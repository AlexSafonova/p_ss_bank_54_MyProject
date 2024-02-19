package com.bank.publicinfo.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
  /*  @Pointcut("execution(* create*(..))")
    public void allAddMethodInServices(){}*/

    @Pointcut("execution(* com.bank.publicinfo.service.*.*(..))")
    public void allMethodsInServices(){}

    @Pointcut("execution(* com.bank.publicinfo.service.AuditService.*(..))")
    public void allMethodsInAuditServices(){}

    @Pointcut("execution(* com.bank.publicinfo.service.*.*(..)) && !allMethodsInAuditServices()")
    public void allMethodsInServicesWithoutAudit(){}

}
