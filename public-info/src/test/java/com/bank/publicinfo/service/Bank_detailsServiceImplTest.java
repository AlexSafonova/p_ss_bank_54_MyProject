package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.entity.Bank_details;
import com.bank.publicinfo.mapper.Bank_detailsMapper;
import com.bank.publicinfo.repository.Bank_detailsRepository;
import com.bank.publicinfo.validation.BankDetailsValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Bank_detailsServiceImplTest {
    @InjectMocks
    Bank_detailsServiceImpl bankDetailsService;
    @Mock
    Bank_detailsRepository bankDetailsRepository;
    @Mock
    BankDetailsValidation bankDetailsValidation;
    @Mock
    Bank_detailsMapper bankDetailsMapper;
    Bank_details bankDetails1 = new Bank_details(1L, 111111L, 11111111L, 111111111L, 11111111, "city1", "jsc1", "name1");
    Bank_details bankDetails2 = new Bank_details(2L, 222222L, 22222222L, 222222222L, 22222222, "city2", "jsc2", "name2");
    Bank_detailsDto bankDetailsDto1 = new Bank_detailsDto(1L, 111111L, 11111111L, 111111111L, 11111111, "city1", "jsc1", "name1");
    Bank_detailsDto bankDetailsDto2 = new Bank_detailsDto(2L, 222222L, 22222222L, 222222222L, 22222222, "city2", "jsc2", "name2");

    @Test
    void getAllBank_details() {
        List<Bank_details> bank_details = List.of(bankDetails1, bankDetails2);
        List<Bank_detailsDto> bankDetailsDtos = List.of(bankDetailsDto1, bankDetailsDto2);
        when(bankDetailsRepository.findAll()).thenReturn(bank_details);
        when(bankDetailsMapper.toListBank_detailsDto(bank_details)).thenReturn(bankDetailsDtos);
        var result = bankDetailsService.getAllBank_details();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findBank_detailsById() {
        when(bankDetailsValidation.findBankDetailsValidator(1L)).thenReturn(bankDetails1);
        doReturn(bankDetailsDto1).when(bankDetailsMapper).toBank_detailsDto(bankDetails1);


        var result = bankDetailsService.findBank_detailsById(1L);

        assertNotNull(result);
        assertEquals(bankDetailsDto1, result);
    }

    @Test
    void addBank_details() {
        doReturn(bankDetails1).when(bankDetailsMapper).toBank_details(bankDetailsDto1);
        when(bankDetailsValidation.createBankDetailsValidator(bankDetails1)).thenReturn(bankDetails1);
        doReturn(bankDetailsDto1).when(bankDetailsMapper).toBank_detailsDto(bankDetails1);
        doReturn(bankDetails1).when(bankDetailsRepository).save(bankDetails1);

        var result = bankDetailsService.addBank_details(bankDetailsDto1);

        assertNotNull(result);
        assertEquals(bankDetailsDto1, result);
    }

    @Test
    void updateBank_details() {
        when(bankDetailsValidation.findBankDetailsValidator(1L)).thenReturn(bankDetails1);
        doReturn(bankDetails1).when(bankDetailsMapper).toBank_details(bankDetailsDto1);
        when(bankDetailsValidation.createBankDetailsValidator(bankDetails1)).thenReturn(bankDetails1);
        doReturn(bankDetailsDto1).when(bankDetailsMapper).toBank_detailsDto(bankDetails1);
        doReturn(bankDetails1).when(bankDetailsRepository).save(bankDetails1);

        var result = bankDetailsService.updateBank_details(1L, bankDetailsDto1);

        assertNotNull(result);
        assertEquals(bankDetailsDto1, result);
    }

    @Test
    void deleteBank_details() {
        when(bankDetailsValidation.findBankDetailsValidator(1L)).thenReturn(bankDetails1);
        bankDetailsService.deleteBank_details(1L);

        verify(bankDetailsRepository).delete(bankDetails1);
    }
}