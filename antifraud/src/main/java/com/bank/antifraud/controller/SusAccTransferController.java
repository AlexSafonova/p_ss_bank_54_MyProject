package com.bank.antifraud.controller;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.util.TransferMock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@Tag(name = "Suspicious Account Transfer Controller", description = "Suspicious Account Transfer Controller")
@RequestMapping("/suspicious-account-transfers")
public class SusAccTransferController {
    private final SuspiciousAccountTransferService suspiciousAccountTransferService;

    @GetMapping("/{id}")
    @Operation(summary = "Get suspicious account transfer",
               description = "Get suspicious account transfer by id")
    public SuspiciousAccountTransfers findById(@PathVariable Long id) {
        return suspiciousAccountTransferService.findSuspiciousTransferById(id);
    }

    @PostMapping
    @Operation(summary = "Create suspicious account transfer",
               description = "Check account transfer and create suspicious account transfer object")
    public SuspiciousAccountTransfers checkSuspiciousTransfer(@RequestBody TransferMock transferMock) {
        return suspiciousAccountTransferService.saveSuspiciousTransfer(transferMock);
    }

    @PutMapping
    @Operation(summary = "Update suspicious account transfer",
               description = "Update suspicious account transfer object")
    public void updateSuspiciousTransfer(@RequestBody SuspiciousAccountTransfers suspiciousAccountTransfer) {
        suspiciousAccountTransferService.updateSuspiciousTransfer(suspiciousAccountTransfer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete suspicious account transfer",
               description = "Delete suspicious account transfer object")
    public void deleteSuspiciousTransfer(@PathVariable Long id) {
        suspiciousAccountTransferService.deleteSuspiciousTransfer(id);
    }
}