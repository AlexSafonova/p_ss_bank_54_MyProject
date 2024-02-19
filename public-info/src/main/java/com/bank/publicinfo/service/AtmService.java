package com.bank.publicinfo.service;


import com.bank.publicinfo.dto.AtmDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AtmService {
    List<AtmDto> getAllAtm();

    AtmDto addAtm(AtmDto atmDto);

    AtmDto updateAtm(Long id, AtmDto atmDto);

    AtmDto findAtmById(Long id);

  //  List<AtmDto> getAllAtmDtoByBranch_id(Long brunch_id);

    void deleteAtm(Long id);
}


