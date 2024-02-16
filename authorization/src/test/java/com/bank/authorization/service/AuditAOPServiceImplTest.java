package com.bank.authorization.service;

import com.bank.authorization.entity.Audit;

import com.bank.authorization.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditAOPServiceImplTest {

    @Mock
    Audit audit;

    @Mock
    AuditRepository auditRepository;

    @InjectMocks
    AuditServiceImpl auditService;

    @Test
    void allAudit() {
        List<Audit> audits = List.of(audit, audit);
        when(auditRepository.findAll()).thenReturn(audits);

        List<Audit> listResult = auditService.allAudit();

        assertEquals(audits, listResult);
        assertNotNull(listResult);
    }

    @Test
    void saveAudit() {
        doReturn(audit).when(auditRepository).save(audit);

        auditRepository.save(audit);
        auditService.saveAudit(audit);

       verify(auditRepository, atLeastOnce()).save(audit);
    }

    @Test
    void getAudit() {
        when(auditRepository.getReferenceById(audit.getId())).thenReturn(audit);

        auditRepository.getReferenceById(audit.getId());
        auditService.getAudit(audit.getId());

        verify(auditRepository, atLeastOnce()).getReferenceById(audit.getId());
    }
}