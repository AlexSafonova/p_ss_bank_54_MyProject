package com.bank.antifraud.service;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.AuditNotFoundException;
import com.bank.antifraud.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuditServiceTest {
    private final AuditRepository mockAuditRepository = Mockito.mock(AuditRepository.class);
    private final AuditService auditService = new AuditService(mockAuditRepository);
    private final Audit audit = new Audit();

    @Test
    public void test_save_audit_non_null_object() {
        // Act
        auditService.saveAudit(audit);

        // Assert
        Mockito.verify(mockAuditRepository, Mockito.times(1)).save(audit);
    }

    @Test
    public void test_returns_audit_object_if_exists() throws AuditNotFoundException {
        // Arrange
        Long id = 1L;
        Audit expectedAudit = new Audit();
        expectedAudit.setId(id);
        when(mockAuditRepository.findById(id)).thenReturn(Optional.of(expectedAudit));

        // Act
        Audit actualAudit = auditService.findAuditById(id);

        // Assert
        assertEquals(expectedAudit, actualAudit);
    }

    @Test
    public void test_throws_audit_not_found_exception_if_not_exists() {
        // Arrange
        Long id = 1L;
        when(mockAuditRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        assertThrows(AuditNotFoundException.class, () -> {
            // Act
            auditService.findAuditById(id);
        });
    }

    @Test
    public void test_returns_list_of_audits() throws AuditNotFoundException {
        // Arrange
        String accountDetailId = "123";
        List<Audit> expectedAudits = new ArrayList<>();
        expectedAudits.add(new Audit());
        Mockito.when(mockAuditRepository.findAllByAccountDetailId(accountDetailId)).thenReturn(expectedAudits);

        // Act
        List<Audit> actualAudits = auditService.findAllByAccountDetailId(accountDetailId);

        // Assert
        assertEquals(expectedAudits, actualAudits);
    }

    @Test
    public void test_throws_audit_not_found_exception() {
        // Arrange
        String accountDetailId = "123";
        Mockito.when(mockAuditRepository.findAllByAccountDetailId(accountDetailId)).thenReturn(Collections.emptyList());

        // Assert
        assertThrows(AuditNotFoundException.class, () -> {
            // Act
            auditService.findAllByAccountDetailId(accountDetailId);
        });
    }
}
