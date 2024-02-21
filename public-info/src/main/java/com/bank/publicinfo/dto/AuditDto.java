package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {
    private Long id;
    private String entity_type;
    @NonNull
    private String operation_type;
    @NonNull
    private String created_by;
    private String modified_by;
    @NonNull
    private Timestamp created_at;
    private Timestamp modified_at;
    private String new_entity_json;
    private String entity_json;
}
