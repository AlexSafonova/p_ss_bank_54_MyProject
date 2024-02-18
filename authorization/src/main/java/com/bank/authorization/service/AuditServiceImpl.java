package com.bank.authorization.service;

import com.bank.authorization.entity.Audit;
import com.bank.authorization.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService{

    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public List<Audit> allAudit() {
        return auditRepository.findAll();
    }

    @Override
    public void saveAudit(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public Audit getAudit(Long id) {
        return auditRepository.getReferenceById(id);
    }
}
