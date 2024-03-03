package com.bank.antifraud.handler;
//демонстрация работы с исключениями

import com.bank.antifraud.exception.AuditNotFoundException;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SuspiciousTransferGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<TransferIncorrectData> handleException(SuspiciousTransferNotFoundException exception) {
        return ResponseEntity.badRequest().body(new TransferIncorrectData(exception.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<TransferIncorrectData> handleException(AuditNotFoundException exception) {
        return ResponseEntity.badRequest().body(new TransferIncorrectData(exception.getMessage()));
    }
}