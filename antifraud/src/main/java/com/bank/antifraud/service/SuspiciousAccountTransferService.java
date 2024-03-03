package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraudpredictor.AccountTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.util.TransferMock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SuspiciousAccountTransferService {
    private final AccountTransferFraudPredictor accountTransferFraudPredictor;
    private final SuspiciousAccountTransferRepository suspiciousTransferRepository;


    @Transactional
    public SuspiciousAccountTransfers saveSuspiciousTransfer(TransferMock transferMock) {
        if (transferMock == null) {
            throw new SuspiciousTransferNotFoundException("Transfer is null");
        }
        SuspiciousAccountTransfers suspiciousTransfer = new SuspiciousAccountTransfers();
        if (accountTransferFraudPredictor.predict(transferMock)) {
            suspiciousTransfer.setSuspicious(true);
            suspiciousTransfer.setSuspiciousReason(transferMock.getSuspiciousReason());
        } else {
            suspiciousTransfer.setSuspicious(false);
            suspiciousTransfer.setSuspiciousReason("Everything is fine");
        }
        suspiciousTransfer.setAccountTransferId(transferMock.getId());
        suspiciousTransfer.setBlocked(false);
        suspiciousTransferRepository.save(suspiciousTransfer);
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.getId() + " saved");
        return suspiciousTransfer;
    }

    @Transactional(readOnly = true)
    public SuspiciousAccountTransfers findSuspiciousTransferById(Long id) {
        Optional <SuspiciousAccountTransfers> suspiciousTransfer = suspiciousTransferRepository.findById(id);
        if (suspiciousTransfer.isEmpty()) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + id + " not found");
        }
        return suspiciousTransfer.get();
    }

    @Transactional
    public void updateSuspiciousTransfer(SuspiciousAccountTransfers suspiciousTransfer) {
        SuspiciousAccountTransfers suspiciousTransfer1 = suspiciousTransferRepository.findById(suspiciousTransfer.getId()).orElse(null);
        if (suspiciousTransfer1 == null) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + suspiciousTransfer.getId() + " not found");
        }
        suspiciousTransfer1.setAccountTransferId(suspiciousTransfer.getAccountTransferId());
        suspiciousTransfer1.setSuspicious(suspiciousTransfer.isSuspicious());
        suspiciousTransfer1.setSuspiciousReason(suspiciousTransfer.getSuspiciousReason());
        suspiciousTransfer1.setBlocked(suspiciousTransfer.isBlocked());
        if (suspiciousTransfer.getBlockReason() != null) {
            suspiciousTransfer1.setBlockReason(suspiciousTransfer.getBlockReason());
        }
        suspiciousTransferRepository.save(suspiciousTransfer1);
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer1.getId() + " updated");
    }

    @Transactional
    public void deleteSuspiciousTransfer(Long id) {
        Optional <SuspiciousAccountTransfers> suspiciousTransfer = suspiciousTransferRepository.findById(id);
        if (suspiciousTransfer.isEmpty()) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + id + " not found");
        }
        suspiciousTransferRepository.delete(suspiciousTransfer.get());
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.get().getId() + " deleted");
    }
}