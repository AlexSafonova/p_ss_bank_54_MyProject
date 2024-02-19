package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.Bank_detailsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Bank_detailsService {
    List<Bank_detailsDto> getAllBank_details();

    Bank_detailsDto findBank_detailsById(Long id);

    Bank_detailsDto addBank_details(Bank_detailsDto bankDetailsDto);

    Bank_detailsDto updateBank_details(Long id, Bank_detailsDto bankDetailsDto);

    void deleteBank_details(Long id);
}
