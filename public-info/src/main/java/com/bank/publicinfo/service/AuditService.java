package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.Audit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuditService {
    List<AuditDto> getAllAudit();

    void addAudit(Audit audit);
}
