package com.bank.authorization.controller;

import com.bank.authorization.entity.Audit;
import com.bank.authorization.service.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditAOPControllerTest {

    @Mock
    Audit audit;

    @Mock
    AuditService auditService;

    @InjectMocks
    AuditController auditController;

    @Test
    void allAudit() {
        List<Audit> audits = List.of(audit,audit);
        when(auditService.allAudit()).thenReturn(audits);

        var response = auditController.allAudit();

        assertNotNull(response);
        assertEquals(audits, response.getBody());


    }

    @Test
    void getAudit() {
        when(auditService.getAudit(audit.getId())).thenReturn(audit);

        var response = auditController.getAudit(audit.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(audit, response.getBody());

    }
}