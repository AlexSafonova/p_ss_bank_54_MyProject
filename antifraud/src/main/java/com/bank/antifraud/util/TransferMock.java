package com.bank.antifraud.util;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
//Это класс - заглушка для работы с приходящими данными от transfer сервиса
public class TransferMock {
    private int id;
    @Min(1)
    @NotBlank
    private int identificationNumber;
    @NotBlank
    private int amount;
    private String purpose;
    @Min(1)
    @NotBlank
    private int accountDetailId;
    @Hidden
    private String suspiciousReason;
}