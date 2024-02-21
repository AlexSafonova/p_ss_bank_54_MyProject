package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface BranchMapper {
    BranchDto toBranchDto(Branch branch);

    Branch toBranch(BranchDto branchDto);

    List<BranchDto> toListBranchDto(List<Branch> list);
}
