package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.validation.AtmValidation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

        return atmMapper.toDto(atmRepository.save(atmValidation.createAtmValidator(atmMapper.toAtm(atmDto))));
    }

    public AtmDto updateAtm(Long id, AtmDto atmDto) {
        atmValidation.findAtmValidator(id);
        atmValidation.createAtmValidator(atmMapper.toAtm(atmDto));
        return atmMapper.toDto(atmRepository.save(atmMapper.toAtm(atmDto)));
    }

    public AtmDto findAtmById(Long id) {
        return atmMapper.toDto(atmValidation.findAtmValidator(id));
    }


    public void deleteAtm(Long id) {
        atmRepository.delete(atmValidation.findAtmValidator(id));
    }
}
