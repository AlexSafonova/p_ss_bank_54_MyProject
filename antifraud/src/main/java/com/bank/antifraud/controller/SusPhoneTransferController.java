package com.bank.antifraud.controller;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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

import java.util.Optional;
@RequiredArgsConstructor
@RestController
@Tag(name = "Suspicious Phone Transfers", description = "Operations related to suspicious phone transfers")
@RequestMapping("/suspicious-phone-transfers")

public class SusPhoneTransferController {
    private final SuspiciousPhoneTransferService suspiciousPhoneTransferService;

    @GetMapping("/{id}")
    @Operation(summary = "Get suspicious phone transfer",
               description = "Get suspicious phone transfer by id")
    public SuspiciousPhoneTransfers findById(@PathVariable Long id) {
        return suspiciousPhoneTransferService.findSuspiciousTransferById(id);
    }

    @PostMapping
    @Operation(summary = "Create suspicious phone transfer",
               description = "Check phone transfer is suspicious and create suspicious phone transfer")
    public SuspiciousPhoneTransfers checkSuspiciousTransfer(@RequestBody TransferMock transferMock) {
        return suspiciousPhoneTransferService.saveSuspiciousTransfer(transferMock);
    }

    @PutMapping
    @Operation(summary = "Update suspicious phone transfer",
               description = "Update suspicious phone transfer object")
    public void updateSuspiciousTransfer(@RequestBody SuspiciousPhoneTransfers suspiciousPhoneTransfer) {
        suspiciousPhoneTransferService.updateSuspiciousTransfer(suspiciousPhoneTransfer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete suspicious phone transfer",
               description = "Delete suspicious phone transfer object")
    public void deleteSuspiciousTransfer(@PathVariable Long id) {
        suspiciousPhoneTransferService.deleteSuspiciousTransfer(id);
    }
}