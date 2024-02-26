package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.Audit;
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
public class AuditMapperImpl implements AuditMapper {

    @Override
    public List<AuditDto> toListAudetDto(List<Audit> list) {
        if ( list == null ) {
            return null;
        }

        List<AuditDto> list1 = new ArrayList<AuditDto>( list.size() );
        for ( Audit audit : list ) {
            list1.add( auditToAuditDto( audit ) );
        }

        return list1;
    }

    protected AuditDto auditToAuditDto(Audit audit) {
        if ( audit == null ) {
            return null;
        }

        AuditDto.AuditDtoBuilder auditDto = AuditDto.builder();

        auditDto.id( audit.getId() );
        auditDto.entity_type( audit.getEntity_type() );
        auditDto.operation_type( audit.getOperation_type() );
        auditDto.created_by( audit.getCreated_by() );
        auditDto.modified_by( audit.getModified_by() );
        auditDto.created_at( audit.getCreated_at() );
        auditDto.modified_at( audit.getModified_at() );
        auditDto.new_entity_json( audit.getNew_entity_json() );
        auditDto.entity_json( audit.getEntity_json() );

        return auditDto.build();
    }
}
