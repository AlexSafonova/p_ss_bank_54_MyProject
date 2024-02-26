package com.bank.antifraud.handler;

//Это исключение просто для демонстрации работы с исключениями
public class TransferIncorrectData {
    private String message;

    public TransferIncorrectData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}