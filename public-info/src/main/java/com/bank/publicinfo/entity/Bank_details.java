package com.bank.publicinfo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@Data
@Table(name = "bank_details",schema = "public_bank_information")
public class Bank_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(unique = true)
    @NonNull
    private Long bik;
    @Column(unique = true)
    @NonNull
    private Long inn;
    @Column(unique = true)
    @NonNull
    private Long kpp;
    @Column(unique = true)
    @NonNull
    private Integer cor_account;
    @NonNull
    @Column(length = 180)
    private String city;
    @NonNull
    @Column(length = 15)
    private String joint_stock_company;
    @NonNull
    @Column(length = 80)
    private String name;
}
