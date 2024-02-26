package com.bank.publicinfo.validation;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.exception.ValidatorException;
import com.bank.publicinfo.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BranchValidation {
    private final BranchRepository branchRepository;

    public Branch createBranchValidator(Branch branch) {
        if (branch != null && !branch.getCity().isBlank()
                && !branch.getAddress().isBlank()
                && branch.getEnd_of_work() != null
                && branch.getStart_of_work() != null
                && branch.getPhone_number() != null) {
            return branch;
        } else {
            throw new ValidatorException("Empty Branch or fields in branch");
        }
    }

    public Branch findBranchValidator(Long id) {
        if (branchRepository.findById(id).isPresent()) {
            return branchRepository.findById(id).get();
        } else {
            throw new NotFoundException("Branch " + id + " not found");
        }
    }
}
