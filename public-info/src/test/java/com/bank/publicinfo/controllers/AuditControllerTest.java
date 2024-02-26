package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {
    @Mock
    AuditService auditService;
    @InjectMocks
    AuditController auditController;
    AuditDto auditDto1 = new AuditDto(1L, "Atm", "getAllAtm", "profile_id", "profile_id", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "111", "111");
    AuditDto auditDto2 = new AuditDto(2L, "Atm", "getAllAtm", "profile_id", "profile_id", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "111", "111");

    @Test
    void getAllAudit() {
        List<AuditDto> auditDtos = List.of(auditDto1, auditDto2);
        when(auditService.getAllAudit()).thenReturn(auditDtos);
        var result = auditController.getAllAudit();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}