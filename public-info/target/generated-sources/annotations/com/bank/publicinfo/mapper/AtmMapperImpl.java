package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
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
public class AtmMapperImpl implements AtmMapper {

    @Override
    public AtmDto toDto(Atm atm) {
        if ( atm == null ) {
            return null;
        }

        AtmDto.AtmDtoBuilder atmDto = AtmDto.builder();

        atmDto.id( atm.getId() );
        atmDto.address( atm.getAddress() );
        atmDto.start_of_work( atm.getStart_of_work() );
        atmDto.end_of_work( atm.getEnd_of_work() );
        atmDto.all_hours( atm.getAll_hours() );

        return atmDto.build();
    }

    @Override
    public Atm toAtm(AtmDto atmDto) {
        if ( atmDto == null ) {
            return null;
        }

        Atm atm = new Atm();

        atm.setId( atmDto.getId() );
        atm.setAddress( atmDto.getAddress() );
        atm.setStart_of_work( atmDto.getStart_of_work() );
        atm.setEnd_of_work( atmDto.getEnd_of_work() );
        atm.setAll_hours( atmDto.getAll_hours() );

        return atm;
    }

    @Override
    public List<AtmDto> toListAtmDto(List<Atm> list) {
        if ( list == null ) {
            return null;
        }

        List<AtmDto> list1 = new ArrayList<AtmDto>( list.size() );
        for ( Atm atm : list ) {
            list1.add( toDto( atm ) );
        }

        return list1;
    }
}
