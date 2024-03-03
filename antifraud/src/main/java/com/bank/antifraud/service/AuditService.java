package com.bank.antifraud.service;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.AuditNotFoundException;
import com.bank.antifraud.repository.AuditRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Getter
@Setter
@RequiredArgsConstructor

public class AuditService {
    private final AuditRepository auditRepository;
    @Transactional
    public void saveAudit(Audit audit) {
        if (audit != null) {
            auditRepository.save(audit);
        }
        Logger.getGlobal().info("Audit with id " + audit.getId() + " saved");
    }

    @Transactional(readOnly = true)
    public Audit findAuditById(Long id) throws AuditNotFoundException {
        Optional <Audit> audit = auditRepository.findById(id);
        if (audit.isEmpty()) {
            throw new AuditNotFoundException("Audit with id " + id + " not found");
        }
        return audit.get();
    }

    @Transactional(readOnly = true)
    public List<Audit> findAllByAccountDetailId(String accountDetailId) throws AuditNotFoundException {
        if (auditRepository.findAllByAccountDetailId(accountDetailId).isEmpty()) {
            throw new AuditNotFoundException("Audits with id " + accountDetailId + " not found");
        }
        return auditRepository.findAllByAccountDetailId(accountDetailId);
    }
}