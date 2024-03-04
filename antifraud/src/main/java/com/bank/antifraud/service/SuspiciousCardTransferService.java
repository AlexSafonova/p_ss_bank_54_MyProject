package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraudpredictor.CardTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.util.TransferMock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor

public class SuspiciousCardTransferService {
    private final CardTransferFraudPredictor cardTransferFraudPredictor;
    private final SuspiciousCardTransferRepository suspiciousTransferRepository;

    @Transactional
    public SuspiciousCardTransfer saveSuspiciousTransfer(TransferMock transferMock) {
        if (transferMock == null) {
            throw new SuspiciousTransferNotFoundException("Transfer is null");
        }
        SuspiciousCardTransfer suspiciousTransfer = new SuspiciousCardTransfer();
        if (cardTransferFraudPredictor.predict(transferMock)) {
            suspiciousTransfer.setSuspicious(true);
            suspiciousTransfer.setSuspiciousReason(transferMock.getSuspiciousReason());
        } else {
            suspiciousTransfer.setSuspicious(false);
            suspiciousTransfer.setSuspiciousReason("Everything is fine");
        }
        suspiciousTransfer.setCardTransferId(transferMock.getId());
        suspiciousTransfer.setBlocked(false);
        suspiciousTransferRepository.save(suspiciousTransfer);
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.getId() + " saved");
        return suspiciousTransfer;
    }

    @Transactional(readOnly = true)
    public SuspiciousCardTransfer findSuspiciousTransferById(Long id) {
        Optional<SuspiciousCardTransfer> suspiciousTransfer = suspiciousTransferRepository.findById(id);
        if (suspiciousTransfer.isEmpty()) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + id + " not found");
        }
        return suspiciousTransfer.get();
    }

    @Transactional
    public void updateSuspiciousTransfer(SuspiciousCardTransfer suspiciousTransfer) {
        if (suspiciousTransfer == null) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer is null");
        }
        SuspiciousCardTransfer suspiciousTransfer1 = suspiciousTransferRepository.findById(suspiciousTransfer.getId()).orElse(null);
        if (suspiciousTransfer1 == null) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + suspiciousTransfer.getId() + " not found");
        }
        suspiciousTransfer1.setCardTransferId(suspiciousTransfer.getCardTransferId());
        suspiciousTransfer1.setSuspicious(suspiciousTransfer.isSuspicious());
        suspiciousTransfer1.setSuspiciousReason(suspiciousTransfer.getSuspiciousReason());
        suspiciousTransfer1.setBlocked(suspiciousTransfer.isBlocked());
        if (suspiciousTransfer.getBlockReason() != null) {
            suspiciousTransfer1.setBlockReason(suspiciousTransfer.getBlockReason());
        }
        suspiciousTransferRepository.save(suspiciousTransfer1);
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.getId() + " updated");
    }

    @Transactional
    public void deleteSuspiciousTransfer(Long id) {
        Optional<SuspiciousCardTransfer> suspiciousTransfer = suspiciousTransferRepository.findById(id);
        if (suspiciousTransfer.isEmpty()) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + id + " not found");
        }
        suspiciousTransferRepository.delete(suspiciousTransfer.get());
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.get().getId() + " deleted");
    }
}