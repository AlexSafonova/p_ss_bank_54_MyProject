package com.bank.publicinfo.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HandlerResponseTest {
    @InjectMocks
    HandlerResponse handlerResponse;
    @Mock
    ExcHandler excHandler;

    @Test
    void getStatus() {
        handlerResponse.getStatus();
    }


    @Test
    void getMessage() {
        handlerResponse.getMessage();
    }

    @Test
    void setStatus() {
        handlerResponse.setStatus(1);
    }

    @Test
    void setStatusNull() {
        handlerResponse.setStatus(0);
    }

    @Test
    void setMessage() {
        handlerResponse.setMessage("1");
    }

    @Test
    void setMessageNull() {
        handlerResponse.setMessage(null);
    }

    @Test
    void testToString() {
        handlerResponse.toString();
    }
}