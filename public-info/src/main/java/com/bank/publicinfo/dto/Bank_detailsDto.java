package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bank_detailsDto {
    private Long id;
    private Long bik;
    private Long inn;
    private Long kpp;
    private Integer cor_account;
    @NonNull
    private String city;
    @NonNull
    private String joint_stock_company;
    @NonNull
    private String name;
}
