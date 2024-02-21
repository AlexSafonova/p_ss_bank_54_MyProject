package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface LicenseMapper {
    LicenseDto toLicenseDto(License license);

    License toLicense(LicenseDto licenseDto);

    List<LicenseDto> toListLicenseDto(List<License> list);
}
