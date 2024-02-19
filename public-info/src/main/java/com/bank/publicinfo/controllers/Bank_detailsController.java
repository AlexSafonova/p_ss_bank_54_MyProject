package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.service.Bank_detailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank_details")
@AllArgsConstructor
public class Bank_detailsController {

    private final Bank_detailsService bankDetailsService;

    @GetMapping
    public List<Bank_detailsDto> getAllBank_details() {
        return bankDetailsService.getAllBank_details();
    }

    @PostMapping
    public Bank_detailsDto addBank_details(@RequestBody Bank_detailsDto bankDetailsDto) {
        return bankDetailsService.addBank_details(bankDetailsDto);
    }

    @GetMapping("/{id}")
    public Bank_detailsDto getBankDetailsById(@PathVariable Long id) {
        return bankDetailsService.findBank_detailsById(id);
    }

    @PatchMapping("/{id}")
    public Bank_detailsDto updateBank_details(@RequestBody Bank_detailsDto bankDetailsDto, @PathVariable Long id) {
        return bankDetailsService.updateBank_details(id, bankDetailsDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBank_details(@PathVariable Long id) {
        bankDetailsService.deleteBank_details(id);
    }
}

