package com.bank.authorization.controller;


import com.bank.authorization.entity.Audit;
import com.bank.authorization.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@Secured("{ROLE_ADMIN}")
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Audit>> allAudit(){
        return ResponseEntity.ok().body(auditService.allAudit());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Audit> getAudit( @PathVariable Long id){
        return ResponseEntity.ok().body(auditService.getAudit(id));
    }

}
