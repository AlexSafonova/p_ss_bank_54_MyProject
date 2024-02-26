package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-26T15:15:32+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class LicenseMapperImpl implements LicenseMapper {

    @Override
    public LicenseDto toLicenseDto(License license) {
        if ( license == null ) {
            return null;
        }

        LicenseDto.LicenseDtoBuilder licenseDto = LicenseDto.builder();

        licenseDto.id( license.getId() );
        Byte[] photo = license.getPhoto();
        if ( photo != null ) {
            licenseDto.photo( Arrays.copyOf( photo, photo.length ) );
        }

        return licenseDto.build();
    }

    @Override
    public License toLicense(LicenseDto licenseDto) {
        if ( licenseDto == null ) {
            return null;
        }

        License license = new License();

        license.setId( licenseDto.getId() );
        Byte[] photo = licenseDto.getPhoto();
        if ( photo != null ) {
            license.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }

        return license;
    }

    @Override
    public List<LicenseDto> toListLicenseDto(List<License> list) {
        if ( list == null ) {
            return null;
        }

        List<LicenseDto> list1 = new ArrayList<LicenseDto>( list.size() );
        for ( License license : list ) {
            list1.add( toLicenseDto( license ) );
        }

        return list1;
    }
}
