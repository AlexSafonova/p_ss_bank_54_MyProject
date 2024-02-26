package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.validation.AtmValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {
    @InjectMocks
    AtmServiceImpl atmServiceImpl;
    @Mock
    AtmMapper atmMapper;
    @Mock
    AtmRepository atmRepository;
    @Mock
    AtmValidation atmValidation;
    Atm atm1 = new Atm(1L, "address1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:59+03:00"), true, null);
    Atm atm2 = new Atm(2L, "address2", OffsetTime.parse("09:00:00+03:00"), OffsetTime.parse("18:00:00+03:00"), false, null);
    AtmDto atmDto1 = new AtmDto(1L, "address1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:59+03:00"), true);
    AtmDto atmDto2 = new AtmDto(2L, "address2", OffsetTime.parse("09:00:00+03:00"), OffsetTime.parse("18:00:00+03:00"), false);

    @Test
    void getAllAtm() throws Throwable {
        List<Atm> atms = List.of(atm1, atm2);
        List<AtmDto> atmDtos = List.of(atmDto1, atmDto2);
        when(atmRepository.findAll()).thenReturn(atms);
        when(atmMapper.toListAtmDto(atms)).thenReturn(atmDtos);
        var result = atmServiceImpl.getAllAtm();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addAtm() {
        doReturn(atm1).when(atmMapper).toAtm(atmDto1);
        when(atmValidation.createAtmValidator(atm1)).thenReturn(atm1);
        doReturn(atmDto1).when(atmMapper).toDto(atm1);
        doReturn(atm1).when(atmRepository).save(atm1);

        var result = atmServiceImpl.addAtm(atmDto1);

        assertNotNull(result);
        assertEquals(atmDto1, result);
    }

    @Test
    void updateAtm() {
        when(atmValidation.findAtmValidator(1L)).thenReturn(atm1);
        doReturn(atm1).when(atmMapper).toAtm(atmDto1);
        when(atmValidation.createAtmValidator(atm1)).thenReturn(atm1);
        doReturn(atmDto1).when(atmMapper).toDto(atm1);
        doReturn(atm1).when(atmRepository).save(atm1);

        var result = atmServiceImpl.updateAtm(1L, atmDto1);

        assertNotNull(result);
        assertEquals(atmDto1, result);
    }

    @Test
    void updateAtmThenAtmValidatirGetNotFindException() {
        when(atmValidation.findAtmValidator(1L)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> atmServiceImpl.updateAtm(1L, atmDto1));
    }

    @Test
    void updateAtmThenAtmValidatirGetValidatorException() {
        when(atmValidation.findAtmValidator(1L)).thenReturn(atm1);
        doReturn(atm1).when(atmMapper).toAtm(atmDto1);
        when(atmValidation.createAtmValidator(atm1)).thenThrow(ValidatorException.class);

        assertThrows(ValidatorException.class, () -> atmServiceImpl.updateAtm(1L, atmDto1));
    }

    @Test
    void findAtmById() {
        when(atmValidation.findAtmValidator(1L)).thenReturn(atm1);
        doReturn(atmDto1).when(atmMapper).toDto(atm1);


        var result = atmServiceImpl.findAtmById(1L);

        assertNotNull(result);
        assertEquals(atmDto1, result);
    }

    @Test
    void findAtmByIdThenFintAtmValidatorGetExceprion() {
        when(atmValidation.findAtmValidator(1L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> atmServiceImpl.findAtmById(1L));
    }

    @Test
    void deleteAtm() {
        when(atmValidation.findAtmValidator(1L)).thenReturn(atm1);
        atmServiceImpl.deleteAtm(1L);

        verify(atmRepository).delete(atm1);
    }

    @Test
    void deleteAtmThenFintAtmValidatorGetExceprion() {
        when(atmValidation.findAtmValidator(1L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> atmServiceImpl.deleteAtm(1L));
    }
}