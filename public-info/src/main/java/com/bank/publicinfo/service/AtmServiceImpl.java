package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.validation.AtmValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;
    private final AtmValidation atmValidation;
    private final AtmMapper atmMapper;

    public List<AtmDto> getAllAtm() {
        return atmMapper.toListAtmDto(atmRepository.findAll());
    }

    public AtmDto addAtm(AtmDto atmDto) {
        Atm atm = validateAndCreateAtm(atmDto);
        return atmMapper.toDto(atmRepository.save(atm));
    }

    public AtmDto updateAtm(Long id, AtmDto atmDto) {
        atmValidation.findAtmValidator(id);
        Atm atm = validateAndCreateAtm(atmDto);
        return atmMapper.toDto(atmRepository.save(atm));
    }

    public AtmDto findAtmById(Long id) {
        return atmMapper.toDto(atmValidation.findAtmValidator(id));
    }


    public void deleteAtm(Long id) {
        atmRepository.delete(atmValidation.findAtmValidator(id));
    }

    private Atm validateAndCreateAtm(AtmDto atmDto) {
        return atmValidation.createAtmValidator(atmMapper.toAtm(atmDto));
    }
}
