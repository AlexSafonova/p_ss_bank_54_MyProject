package com.bank.authorization.handler;

import com.bank.authorization.exeptions.NoUserException;
import com.bank.authorization.exeptions.UserIncorrect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    NoUserException noUserException;

    @Mock
    Exception exception;

    @InjectMocks
    GlobalExceptionHandler exceptionHandler;

    @Test
    void handlerException() {
        ResponseEntity<UserIncorrect> responseEntity = exceptionHandler.handlerException(noUserException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(noUserException.getMessage(), responseEntity.getBody().getInfo());

    }

    @Test
    void testHandlerException() {
        ResponseEntity<UserIncorrect> responseEntity = exceptionHandler.handlerException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}