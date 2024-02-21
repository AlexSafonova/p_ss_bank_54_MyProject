package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.entity.Bank_details;
import com.bank.publicinfo.mapper.Bank_detailsMapper;
import com.bank.publicinfo.repository.Bank_detailsRepository;
import com.bank.publicinfo.validation.BankDetailsValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class Bank_detailsServiceImpl implements Bank_detailsService {

    private final Bank_detailsRepository bankDetailsRepository;
    private final Bank_detailsMapper bankDetailsMapper;
    private final BankDetailsValidation bankDetailsValidation;


    public List<Bank_detailsDto> getAllBank_details() {
        return bankDetailsMapper.toListBank_detailsDto(bankDetailsRepository.findAll());
    }

    public Bank_detailsDto findBank_detailsById(Long id) {
        return bankDetailsMapper.toBank_detailsDto(bankDetailsValidation.findBankDetailsValidator(id));
    }

    public Bank_detailsDto addBank_details(Bank_detailsDto bankDetailsDto) {
        Bank_details bankDetails = validateAndCreateBank_details(bankDetailsDto);
        return bankDetailsMapper.toBank_detailsDto(bankDetailsRepository.save(bankDetails));
    }

    public Bank_detailsDto updateBank_details(Long id, Bank_detailsDto bankDetailsDto) {
        bankDetailsValidation.findBankDetailsValidator(id);
        Bank_details bankDetails = validateAndCreateBank_details(bankDetailsDto);
        return bankDetailsMapper.toBank_detailsDto(bankDetailsRepository.save(bankDetails));
    }

    public void deleteBank_details(Long id) {
        bankDetailsRepository.delete(bankDetailsValidation.findBankDetailsValidator(id));
    }

    private Bank_details validateAndCreateBank_details(Bank_detailsDto bankDetailsDto) {
        return bankDetailsValidation.createBankDetailsValidator(bankDetailsMapper.toBank_details(bankDetailsDto));
    }
}
