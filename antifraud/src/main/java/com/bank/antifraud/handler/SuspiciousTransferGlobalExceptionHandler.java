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
        TransferIncorrectData transferIncorrectData = new TransferIncorrectData(exception.getMessage());
        return ResponseEntity.badRequest().body(transferIncorrectData);
    }

    @ExceptionHandler
    public ResponseEntity<TransferIncorrectData> handleException(AuditNotFoundException exception) {
        TransferIncorrectData transferIncorrectData = new TransferIncorrectData(exception.getMessage());
        return ResponseEntity.badRequest().body(transferIncorrectData);
    }

}