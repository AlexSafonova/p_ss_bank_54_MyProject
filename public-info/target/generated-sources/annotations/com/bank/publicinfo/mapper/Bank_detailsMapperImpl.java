package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.entity.Bank_details;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-26T15:15:32+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class Bank_detailsMapperImpl implements Bank_detailsMapper {

    @Override
    public Bank_detailsDto toBank_detailsDto(Bank_details bankDetails) {
        if ( bankDetails == null ) {
            return null;
        }

        Bank_detailsDto.Bank_detailsDtoBuilder bank_detailsDto = Bank_detailsDto.builder();

        bank_detailsDto.id( bankDetails.getId() );
        bank_detailsDto.bik( bankDetails.getBik() );
        bank_detailsDto.inn( bankDetails.getInn() );
        bank_detailsDto.kpp( bankDetails.getKpp() );
        bank_detailsDto.cor_account( bankDetails.getCor_account() );
        bank_detailsDto.city( bankDetails.getCity() );
        bank_detailsDto.joint_stock_company( bankDetails.getJoint_stock_company() );
        bank_detailsDto.name( bankDetails.getName() );

        return bank_detailsDto.build();
    }

    @Override
    public Bank_details toBank_details(Bank_detailsDto bankDetailsDto) {
        if ( bankDetailsDto == null ) {
            return null;
        }

        Bank_details bank_details = new Bank_details();

        bank_details.setId( bankDetailsDto.getId() );
        bank_details.setBik( bankDetailsDto.getBik() );
        bank_details.setInn( bankDetailsDto.getInn() );
        bank_details.setKpp( bankDetailsDto.getKpp() );
        bank_details.setCor_account( bankDetailsDto.getCor_account() );
        bank_details.setCity( bankDetailsDto.getCity() );
        bank_details.setJoint_stock_company( bankDetailsDto.getJoint_stock_company() );
        bank_details.setName( bankDetailsDto.getName() );

        return bank_details;
    }

    @Override
    public List<Bank_detailsDto> toListBank_detailsDto(List<Bank_details> list) {
        if ( list == null ) {
            return null;
        }

        List<Bank_detailsDto> list1 = new ArrayList<Bank_detailsDto>( list.size() );
        for ( Bank_details bank_details : list ) {
            list1.add( toBank_detailsDto( bank_details ) );
        }

        return list1;
    }
}
