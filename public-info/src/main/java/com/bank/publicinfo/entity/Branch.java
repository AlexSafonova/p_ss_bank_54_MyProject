package com.bank.publicinfo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "branch",schema = "public_bank_information")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @NonNull
    @Column(length = 370)
    private String address;
    @NonNull
    @Column(unique = true)
    private Long phone_number;
    @NonNull
    @Column
    private String city;
    @Column(columnDefinition = "TIME WITH TIME ZONE", length = 6)
    private Time start_of_work;
    @Column(columnDefinition = "TIME WITH TIME ZONE", length = 6)
    private Time end_of_work;
}
