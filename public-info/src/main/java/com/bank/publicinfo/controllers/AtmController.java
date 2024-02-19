package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.AtmService;
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
@RequestMapping("/atm")
@AllArgsConstructor
public class AtmController {
    private final AtmService atmService;

    @GetMapping
    public List<AtmDto> getAllAtm() {
        return atmService.getAllAtm();
    }

    @PostMapping
    public AtmDto addAtm(@RequestBody AtmDto atmDto) {
        return atmService.addAtm(atmDto);
    }

    @GetMapping("/{id}")
    public AtmDto getAtmById(@PathVariable Long id) {
        return atmService.findAtmById(id);
    }

    @PatchMapping("/{id}")
    public AtmDto updateAtm(@RequestBody AtmDto atmDto, @PathVariable Long id) {
        return atmService.updateAtm(id, atmDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAtm(@PathVariable Long id) {
        atmService.deleteAtm(id);
    }
}
