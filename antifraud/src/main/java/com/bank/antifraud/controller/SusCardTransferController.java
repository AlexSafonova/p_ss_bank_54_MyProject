package com.bank.antifraud.controller;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.antifraud.util.TransferMock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Suspicious Card Transfers", description = "Suspicious Card Transfers API")
@RequestMapping("/suspicious-card-transfers")
public class SusCardTransferController {
    private final SuspiciousCardTransferService suspiciousCardTransferService;

    @GetMapping("/{id}")
    @Operation(summary = "Get suspicious transfer",
            description = "Get suspicious transfer by id")
    public SuspiciousCardTransfer findById(@PathVariable Long id) {
        return suspiciousCardTransferService.findSuspiciousTransferById(id);
    }

    @PostMapping
    @Operation(summary = "Create suspicious transfer",
            description = "Check card transfer is suspicious and save suspicious transfer")
    public SuspiciousCardTransfer checkSuspiciousTransfer(@RequestBody TransferMock transferMock) {
        return suspiciousCardTransferService.saveSuspiciousTransfer(transferMock);

    }

    @PutMapping
    @Operation(summary = "Update suspicious transfer",
            description = "Update suspicious transfer object")
    public void updateSuspiciousTransfer(@RequestBody SuspiciousCardTransfer suspiciousCardTransfer) {
        suspiciousCardTransferService.updateSuspiciousTransfer(suspiciousCardTransfer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete suspicious transfer",
            description = "Delete suspicious transfer object")
    public void deleteSuspiciousTransfer(@PathVariable Long id) {
        suspiciousCardTransferService.deleteSuspiciousTransfer(id);
    }

}