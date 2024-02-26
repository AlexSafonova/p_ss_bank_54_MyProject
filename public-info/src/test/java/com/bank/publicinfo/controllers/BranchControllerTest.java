package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {
    @InjectMocks
    BranchController branchController;
    @Mock
    BranchService branchService;
    @Mock
    BranchDto branchDto;

    @Test
    void getAllBranch() {
        List<BranchDto> branchDtos = List.of(branchDto, branchDto);
        when(branchService.getAllBranch()).thenReturn(branchDtos);
        var result = branchController.getAllBranch();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addBranch() {
        when(branchService.addBranch(branchDto)).thenReturn(branchDto);
        var result = branchController.addBranch(branchDto);

        assertNotNull(result);
        assertEquals(branchDto, result);
    }

    @Test
    void getBranchById() {
        when(branchService.findBranchById(1L)).thenReturn(branchDto);
        var result = branchController.getBranchById(1L);

        assertNotNull(result);
        assertEquals(branchDto, result);
    }

    @Test
    void updateBranch() {
        when(branchService.updateBranch(1L, branchDto)).thenReturn(branchDto);
        var result = branchController.updateBranch(branchDto, 1L);

        assertNotNull(result);
        assertEquals(branchDto, result);
    }

    @Test
    void deleteBranch() {
        branchController.deleteBranch(1L);

        verify(branchService).deleteBranch(1L);
    }
}