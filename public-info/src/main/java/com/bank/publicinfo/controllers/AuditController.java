package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@AllArgsConstructor
public class AuditController {
    private final AuditService auditService;
    @Operation(summary = "Get All audit")
    @GetMapping
    public List<AuditDto> getAllAtm() {
        return auditService.getAllAudit();
    }
}
