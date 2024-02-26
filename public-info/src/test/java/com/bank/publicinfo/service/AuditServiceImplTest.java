package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.mapper.AuditMapper;
import com.bank.publicinfo.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {
    @InjectMocks
    AuditServiceImpl auditServiceImpl;
    @Mock
    AuditRepository auditRepository;
    @Mock
    AuditMapper auditMapper;
    @Mock
    Audit audit;
    @Mock
    AuditDto auditDto;

    @Test
    void getAllAudit() {
        List<Audit> audits = List.of(audit, audit);
        List<AuditDto> auditDtos = List.of(auditDto, auditDto);
        when(auditRepository.findAll()).thenReturn(audits);
        when(auditMapper.toListAudetDto(audits)).thenReturn(auditDtos);
        var result = auditServiceImpl.getAllAudit();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addAudit() {
        auditServiceImpl.addAudit(audit);

        verify(auditRepository).save(audit);
    }
}