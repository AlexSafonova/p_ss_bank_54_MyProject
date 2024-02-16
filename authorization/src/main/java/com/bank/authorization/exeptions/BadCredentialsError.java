package com.bank.authorization.exeptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BadCredentialsError  {
    private int status;
    private String message;
    private Date time;

    public BadCredentialsError(int status, String message, Date time) {
        this.status = status;
        this.message = message;
        this.time = new Date();
    }
}
