package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.service.Bank_detailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Bank_detailsControllerTest {
    @InjectMocks
    Bank_detailsController bankDetailsController;
    @Mock
    Bank_detailsService bankDetailsService;
    @Mock
    Bank_detailsDto bankDetailsDto;

    @Test
    void getAllBank_details() {
        List<Bank_detailsDto> bankDetailsDtos = List.of(bankDetailsDto, bankDetailsDto);
        when(bankDetailsService.getAllBank_details()).thenReturn(bankDetailsDtos);
        var result = bankDetailsController.getAllBank_details();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addBank_details() {
        when(bankDetailsService.addBank_details(bankDetailsDto)).thenReturn(bankDetailsDto);
        var result = bankDetailsController.addBank_details(bankDetailsDto);

        assertNotNull(result);
        assertEquals(bankDetailsDto, result);
    }

    @Test
    void getBankDetailsById() {
        when(bankDetailsService.findBank_detailsById(1L)).thenReturn(bankDetailsDto);
        var result = bankDetailsController.getBankDetailsById(1L);

        assertNotNull(result);
        assertEquals(bankDetailsDto, result);
    }

    @Test
    void updateBank_details() {
        when(bankDetailsService.updateBank_details(1L, bankDetailsDto)).thenReturn(bankDetailsDto);
        var result = bankDetailsController.updateBank_details(bankDetailsDto, 1L);

        assertNotNull(result);
        assertEquals(bankDetailsDto, result);
    }

    @Test
    void deleteBank_details() {
        bankDetailsController.deleteBank_details(1L);

        verify(bankDetailsService).deleteBank_details(1L);
    }
}