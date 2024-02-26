package com.bank.publicinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "bank_details", schema = "public_bank_information")
public class Bank_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(unique = true)

    private Long bik;
    @Column(unique = true)

    private Long inn;
    @Column(unique = true)

    private Long kpp;
    @Column(unique = true)

    private Integer cor_account;

    @Column(length = 180)
    private String city;

    @Column(length = 15)
    private String joint_stock_company;

    @Column(length = 80)
    private String name;
}
