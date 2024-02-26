package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
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
public class BranchMapperImpl implements BranchMapper {

    @Override
    public BranchDto toBranchDto(Branch branch) {
        if ( branch == null ) {
            return null;
        }

        BranchDto.BranchDtoBuilder branchDto = BranchDto.builder();

        branchDto.id( branch.getId() );
        branchDto.address( branch.getAddress() );
        branchDto.phone_number( branch.getPhone_number() );
        branchDto.city( branch.getCity() );
        branchDto.start_of_work( branch.getStart_of_work() );
        branchDto.end_of_work( branch.getEnd_of_work() );

        return branchDto.build();
    }

    @Override
    public Branch toBranch(BranchDto branchDto) {
        if ( branchDto == null ) {
            return null;
        }

        Branch branch = new Branch();

        branch.setId( branchDto.getId() );
        branch.setAddress( branchDto.getAddress() );
        branch.setPhone_number( branchDto.getPhone_number() );
        branch.setCity( branchDto.getCity() );
        branch.setStart_of_work( branchDto.getStart_of_work() );
        branch.setEnd_of_work( branchDto.getEnd_of_work() );

        return branch;
    }

    @Override
    public List<BranchDto> toListBranchDto(List<Branch> list) {
        if ( list == null ) {
            return null;
        }

        List<BranchDto> list1 = new ArrayList<BranchDto>( list.size() );
        for ( Branch branch : list ) {
            list1.add( toBranchDto( branch ) );
        }

        return list1;
    }
}
