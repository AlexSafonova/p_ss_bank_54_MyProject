package com.bank.authorization.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Pointcuts {
    @Pointcut("execution(* com.bank.authorization.service.UserService.registerUser(..))")
    public void save(){}

    @Pointcut("execution(* com.bank.authorization.service.UserService.updateUser(..))")
    public void update(){}
}
