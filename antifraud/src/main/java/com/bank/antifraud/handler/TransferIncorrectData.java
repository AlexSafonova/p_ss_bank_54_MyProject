package com.bank.antifraud.handler;

import lombok.Getter;

//Это исключение просто для демонстрации работы с исключениями
@Getter
public class TransferIncorrectData {
    private String message;

    public TransferIncorrectData(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
