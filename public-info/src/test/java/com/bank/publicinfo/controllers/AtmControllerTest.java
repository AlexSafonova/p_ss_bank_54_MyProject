package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.AtmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtmControllerTest {
    @Mock
    AtmService atmService;
    @InjectMocks
    AtmController atmController;


    AtmDto atmDto1 = new AtmDto(1L, "address1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:59+03:00"), true);
    AtmDto atmDto2 = new AtmDto(2L, "address2", OffsetTime.parse("09:00:00+03:00"), OffsetTime.parse("18:00:00+03:00"), false);

    @Test
    void getAllAtm() {
        List<AtmDto> atmDtos = List.of(atmDto1, atmDto2);
        when(atmService.getAllAtm()).thenReturn(atmDtos);
        var result = atmController.getAllAtm();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addAtm() {
        when(atmService.addAtm(atmDto1)).thenReturn(atmDto1);
        var result = atmController.addAtm(atmDto1);

        assertNotNull(result);
        assertEquals(atmDto1, result);
    }

    @Test
    void getAtmById() {
        when(atmService.findAtmById(1L)).thenReturn(atmDto1);
        var result = atmController.getAtmById(1L);

        assertNotNull(result);
        assertEquals(atmDto1, result);
    }

    @Test
    void updateAtm() {
        when(atmService.updateAtm(1L, atmDto1)).thenReturn(atmDto1);
        var result = atmController.updateAtm(atmDto1, 1L);

        assertNotNull(result);
        assertEquals(atmDto1, result);
    }

    @Test
    void deleteAtm() {
        atmController.deleteAtm(1L);

        verify(atmService).deleteAtm(1L);
    }
}