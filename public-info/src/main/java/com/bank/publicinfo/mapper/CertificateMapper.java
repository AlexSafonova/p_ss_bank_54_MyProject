package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CertificateMapper {
    CertificateDto toCertificateDto(Certificate certificate);

    Certificate toCertificate(CertificateDto certificateDto);

    List<CertificateDto> toListCertificateDto(List<Certificate> list);
}
