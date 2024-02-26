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
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit", schema = "public_bank_information")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @NonNull
    @Column(length = 40)
    private String entity_type;
    @NonNull
    private String operation_type;
    @NonNull
    private String created_by;
    private String modified_by;
    @NonNull
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp created_at;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp modified_at;
    private String new_entity_json;
    @NonNull
    private String entity_json;
}

