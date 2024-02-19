package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;
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
        return branchMapper.toBranchDto(branchRepository.save
                (branchValidation.createBranchValidator(branchMapper.toBranch(branchDto))));
    }

    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        branchValidation.findBranchValidator(id);
        return branchMapper.toBranchDto(branchRepository.save
                (branchValidation.createBranchValidator(branchMapper.toBranch(branchDto))));
    }

    public void deleteBranch(Long id) {
        branchRepository.delete(branchValidation.findBranchValidator(id));
    }

}
