package com.bank.antifraud.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "suspicious_card_transfers")

public class SuspiciousCardTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_transfer_id", unique = true, nullable = false)
    private int cardTransferId;
    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;
    @Column(name = "is_suspicious", nullable = false)
    private boolean isSuspicious;
    @Column(name = "block_reason")
    private String blockReason;
    @Column(name = "suspicious_reason", nullable = false)
    private String suspiciousReason;
}