package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchService {
    List<BranchDto> getAllBranch();

    BranchDto findBranchById(Long id);

    BranchDto addBranch(BranchDto branchDto);

    BranchDto updateBranch(Long id, BranchDto branchDto);

    void deleteBranch(Long id);
}
