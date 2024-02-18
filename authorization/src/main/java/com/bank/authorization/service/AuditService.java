package com.bank.authorization.service;

import com.bank.authorization.entity.Audit;

import java.util.List;

public interface AuditService {
    List<Audit> allAudit();
    void saveAudit(Audit audit);
    Audit getAudit(Long id);

}
