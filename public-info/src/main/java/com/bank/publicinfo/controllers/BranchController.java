package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branch")
@AllArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @Operation(summary = "Get all branch")
    @GetMapping
    public List<BranchDto> getAllBranch() {
        return branchService.getAllBranch();
    }

    @Operation(summary = "Add branch")
    @PostMapping
    public BranchDto addBranch(@RequestBody BranchDto branchDto) {
        return branchService.addBranch(branchDto);
    }

    @Operation(summary = "Get branch by id")
    @GetMapping("/{id}")
    public BranchDto getBranchById(@PathVariable Long id) {
        return branchService.findBranchById(id);
    }

    @Operation(summary = "Update branch by id")
    @PatchMapping("/{id}")
    public BranchDto updateBranch(@RequestBody BranchDto branchDto, @PathVariable Long id) {
        return branchService.updateBranch(id, branchDto);
    }

    @Operation(summary = "Delete branch by id")
    @DeleteMapping("/{id}")
    public void deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
    }
}
