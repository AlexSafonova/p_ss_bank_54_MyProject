package com.bank.authorization.exeptions;

public class NoUserException extends RuntimeException{

    public NoUserException(String message) {
        super(message);
    }
}
