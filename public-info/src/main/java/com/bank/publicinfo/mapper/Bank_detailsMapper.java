package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.entity.Bank_details;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface Bank_detailsMapper {
    Bank_detailsDto toBank_detailsDto(Bank_details bankDetails);

    Bank_details toBank_details(Bank_detailsDto bankDetailsDto);

    List<Bank_detailsDto> toListBank_detailsDto(List<Bank_details> list);
}
