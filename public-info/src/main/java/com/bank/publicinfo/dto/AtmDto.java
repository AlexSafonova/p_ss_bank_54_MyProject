package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.Branch;
import lombok.*;

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
