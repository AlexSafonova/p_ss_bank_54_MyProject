package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExcHandlerTest {
    @InjectMocks
    ExcHandler excHandler;
    @Mock
    MethodArgumentTypeMismatchException methodArgumentTypeMismatchException;
    @Mock
    NotFoundException notFoundException;

    @Mock
    ValidatorException validatorException;


    @Test
    void handleRequestParam() {

        ResponseEntity<HandlerResponse> responseEntity = excHandler.handleRequestParam(methodArgumentTypeMismatchException);

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(methodArgumentTypeMismatchException.getMessage(), responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleNotFound() {
        ResponseEntity<HandlerResponse> responseEntity = excHandler.handleNotFound(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
        assertEquals(notFoundException.getMessage(), responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void handleValidation() {
        ResponseEntity<HandlerResponse> responseEntity = excHandler.handleValidation(validatorException);

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(validatorException.getMessage(), responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}