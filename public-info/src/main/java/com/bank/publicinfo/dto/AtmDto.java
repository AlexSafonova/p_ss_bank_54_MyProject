package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmDto {
    private Long id;

    @NonNull
    private String address;

    private Time start_of_work;

    private Time end_of_work;

    @NonNull
    private Boolean all_hours;

}
