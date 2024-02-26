package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface AtmMapper {
    AtmDto toDto(Atm atm);

    Atm toAtm(AtmDto atmDto);

    List<AtmDto> toListAtmDto(List<Atm> list);
}
