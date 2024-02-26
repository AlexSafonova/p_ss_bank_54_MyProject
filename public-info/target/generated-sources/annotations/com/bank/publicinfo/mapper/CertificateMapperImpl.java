package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-26T15:15:33+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public CertificateDto toCertificateDto(Certificate certificate) {
        if ( certificate == null ) {
            return null;
        }

        CertificateDto.CertificateDtoBuilder certificateDto = CertificateDto.builder();

        certificateDto.id( certificate.getId() );
        Byte[] photo = certificate.getPhoto();
        if ( photo != null ) {
            certificateDto.photo( Arrays.copyOf( photo, photo.length ) );
        }

        return certificateDto.build();
    }

    @Override
    public Certificate toCertificate(CertificateDto certificateDto) {
        if ( certificateDto == null ) {
            return null;
        }

        Certificate certificate = new Certificate();

        certificate.setId( certificateDto.getId() );
        Byte[] photo = certificateDto.getPhoto();
        if ( photo != null ) {
            certificate.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }

        return certificate;
    }

    @Override
    public List<CertificateDto> toListCertificateDto(List<Certificate> list) {
        if ( list == null ) {
            return null;
        }

        List<CertificateDto> list1 = new ArrayList<CertificateDto>( list.size() );
        for ( Certificate certificate : list ) {
            list1.add( toCertificateDto( certificate ) );
        }

        return list1;
    }
}
