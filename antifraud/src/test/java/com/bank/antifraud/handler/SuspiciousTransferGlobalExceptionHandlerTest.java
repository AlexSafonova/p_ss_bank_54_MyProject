package com.bank.antifraud.handler;

import com.bank.antifraud.exception.AuditNotFoundException;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SuspiciousTransferGlobalExceptionHandlerTest {
    private final SuspiciousTransferGlobalExceptionHandler handler = new SuspiciousTransferGlobalExceptionHandler();

    @Test
    public void test_return_response_entity() {
        SuspiciousTransferNotFoundException exception = new SuspiciousTransferNotFoundException("Exception message");

        ResponseEntity<TransferIncorrectData> responseEntity = handler.handleException(exception);

        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Exception message", responseEntity.getBody().getMessage());
    }

    @Test
    public void test_handleException_SuspiciousTransferNotFoundException() {
        // Arrange
        SuspiciousTransferNotFoundException exception = new SuspiciousTransferNotFoundException("Test Exception");

        // Act
        ResponseEntity<TransferIncorrectData> response = handler.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Exception", response.getBody().getMessage());
    }

    @Test
    public void test_handleException_MultipleExceptions() {
        // Arrange
        SuspiciousTransferNotFoundException exception1 = new SuspiciousTransferNotFoundException("Test Exception 1");
        AuditNotFoundException exception2 = new AuditNotFoundException("Test Exception 2");

        // Act
        ResponseEntity<TransferIncorrectData> response1 = handler.handleException(exception1);
        ResponseEntity<TransferIncorrectData> response2 = handler.handleException(exception2);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertEquals("Test Exception 1", response1.getBody().getMessage());

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertEquals("Test Exception 2", response2.getBody().getMessage());
    }
}
