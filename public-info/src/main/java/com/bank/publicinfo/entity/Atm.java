package com.bank.publicinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Time;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atm",schema = "public_bank_information")
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NonNull
    @Column(length = 370)
    private String address;
    @Column(columnDefinition = "TIME WITH TIME ZONE", length = 6)
    private Time start_of_work;
    @Column(columnDefinition = "TIME WITH TIME ZONE", length = 6)
    private Time end_of_work;

    @NonNull
    @Column
    private Boolean all_hours;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
