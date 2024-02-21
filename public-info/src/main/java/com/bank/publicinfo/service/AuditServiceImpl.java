package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.mapper.AuditMapper;
import com.bank.publicinfo.repository.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bank.publicinfo.dto.AuditDto;
import java.util.List;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditRepository auditRepository, AuditMapper auditMapper) {
        this.auditRepository = auditRepository;
        this.auditMapper = auditMapper;
    }


    @Override
    public List<AuditDto> getAllAudit() {
        return auditMapper.toListAudetDto(auditRepository.findAll());
    }
    @Override
    public void addAudit(Audit audit) {
        auditRepository.save(audit);
    }

}
