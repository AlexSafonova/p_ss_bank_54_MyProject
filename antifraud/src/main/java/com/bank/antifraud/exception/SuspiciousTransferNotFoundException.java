package com.bank.antifraud.exception;

//Это исключение просто для демонстрации работы с исключениями
public class SuspiciousTransferNotFoundException extends RuntimeException {
    public SuspiciousTransferNotFoundException(String message) {
        super(message);
    }
}