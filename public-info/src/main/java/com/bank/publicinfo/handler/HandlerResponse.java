package com.bank.publicinfo.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HandlerResponse {
    private int status;
    private String message;
}
