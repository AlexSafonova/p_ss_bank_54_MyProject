package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.fraud_predictor.PhoneTransferFraudPredictor;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.util.TransferMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service

public class SuspiciousPhoneTransferService{
    private final PhoneTransferFraudPredictor phoneTransferFraudPredictor;
    private final SuspiciousPhoneTransferRepository suspiciousTransferRepository;
    @Autowired

    public SuspiciousPhoneTransferService(PhoneTransferFraudPredictor phoneTransferFraudPredictor, SuspiciousPhoneTransferRepository suspiciousTransferRepository) {
        this.phoneTransferFraudPredictor = phoneTransferFraudPredictor;
        this.suspiciousTransferRepository = suspiciousTransferRepository;
    }

    @Transactional
    public SuspiciousPhoneTransfers saveSuspiciousTransfer(TransferMock transferMock) {
        if (transferMock == null) {
            throw new SuspiciousTransferNotFoundException("Transfer is null");
        }
        SuspiciousPhoneTransfers suspiciousTransfer = new SuspiciousPhoneTransfers();
        if (phoneTransferFraudPredictor.predict(transferMock)) {
            suspiciousTransfer.setSuspiciousReason(transferMock.getSuspiciousReason());
            suspiciousTransfer.setSuspicious(true);

        } else {
            suspiciousTransfer.setSuspicious(false);
            suspiciousTransfer.setSuspiciousReason("Everything is fine");
        }
        suspiciousTransfer.setPhoneTransferId(transferMock.getId());
        suspiciousTransfer.setBlocked(false);
        suspiciousTransferRepository.save(suspiciousTransfer);
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.getId() + " saved");
        return suspiciousTransfer;
    }

    @Transactional(readOnly = true)
    public SuspiciousPhoneTransfers findSuspiciousTransferById(Long id) {
        if (suspiciousTransferRepository.findById(id).isEmpty()) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + id + " not found");
        }
        return suspiciousTransferRepository.findById(id).get();
    }

    @Transactional
    public void updateSuspiciousTransfer(SuspiciousPhoneTransfers suspiciousTransfer) {
        if (suspiciousTransfer == null) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer is null");
        }
        SuspiciousPhoneTransfers suspiciousTransfer1 = suspiciousTransferRepository.findById(suspiciousTransfer.getId()).orElse(null);
        if (suspiciousTransfer1 == null) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + suspiciousTransfer.getId() + " not found");
        }
        suspiciousTransfer1.setPhoneTransferId(suspiciousTransfer.getPhoneTransferId());
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
        if (suspiciousTransferRepository.findById(id).isEmpty()) {
            throw new SuspiciousTransferNotFoundException("Suspicious transfer with id " + id + " not found");
        }
        SuspiciousPhoneTransfers suspiciousTransfer = suspiciousTransferRepository.findById(id).orElse(null);
        suspiciousTransferRepository.delete(suspiciousTransfer);
        Logger.getGlobal().info("Suspicious transfer with id " + suspiciousTransfer.getId() + " deleted");
    }
}