package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.Bank_detailsDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Bank_details;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.validation.BranchValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    private final BranchValidation branchValidation;

    public List<BranchDto> getAllBranch() {
        return branchMapper.toListBranchDto(branchRepository.findAll());
    }

    public BranchDto findBranchById(Long id) {
        return branchMapper.toBranchDto(branchValidation.findBranchValidator(id));
    }

    public BranchDto addBranch(BranchDto branchDto) {
        Branch branch = validateAndCreateBranch(branchDto);
        return branchMapper.toBranchDto(branchRepository.save(branch));
    }

    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        branchValidation.findBranchValidator(id);
        Branch branch = validateAndCreateBranch(branchDto);
        return branchMapper.toBranchDto(branchRepository.save(branch));
    }

    public void deleteBranch(Long id) {
        branchRepository.delete(branchValidation.findBranchValidator(id));
    }

    private Branch validateAndCreateBranch(BranchDto branchDto) {
        return branchValidation.createBranchValidator(branchMapper.toBranch(branchDto));
    }
}
