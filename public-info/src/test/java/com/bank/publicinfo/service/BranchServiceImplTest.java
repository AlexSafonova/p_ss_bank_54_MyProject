package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.validation.BranchValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {
    @InjectMocks
    BranchServiceImpl branchService;
    @Mock
    BranchRepository branchRepository;
    @Mock
    BranchValidation branchValidation;
    @Mock
    BranchMapper branchMapper;

    Branch branch1 = new Branch(1L, "address1", 1234567L, "city1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));
    Branch branch2 = new Branch(2L, "address2", 1234567L, "city2", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));
    BranchDto branchDto1 = new BranchDto(1L, "address1", 1234567L, "city1", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));
    BranchDto branchDto2 = new BranchDto(2L, "address2", 1234567L, "city2", OffsetTime.parse("00:00:00+03:00"), OffsetTime.parse("23:59:00+03:00"));

    @Test
    void getAllBranch() {
        List<Branch> branches = List.of(branch1, branch2);
        List<BranchDto> branchDtos = List.of(branchDto1, branchDto2);
        when(branchRepository.findAll()).thenReturn(branches);
        when(branchMapper.toListBranchDto(branches)).thenReturn(branchDtos);
        var result = branchService.getAllBranch();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findBranchById() {
        when(branchValidation.findBranchValidator(1L)).thenReturn(branch1);
        doReturn(branchDto1).when(branchMapper).toBranchDto(branch1);


        var result = branchService.findBranchById(1L);

        assertNotNull(result);
        assertEquals(branchDto1, result);
    }

    @Test
    void addBranch() {
        doReturn(branch1).when(branchMapper).toBranch(branchDto1);
        when(branchValidation.createBranchValidator(branch1)).thenReturn(branch1);
        doReturn(branchDto1).when(branchMapper).toBranchDto(branch1);
        doReturn(branch1).when(branchRepository).save(branch1);

        var result = branchService.addBranch(branchDto1);

        assertNotNull(result);
        assertEquals(branchDto1, result);
    }

    @Test
    void updateBranch() {
        when(branchValidation.findBranchValidator(1L)).thenReturn(branch1);
        doReturn(branch1).when(branchMapper).toBranch(branchDto1);
        when(branchValidation.createBranchValidator(branch1)).thenReturn(branch1);
        doReturn(branchDto1).when(branchMapper).toBranchDto(branch1);
        doReturn(branch1).when(branchRepository).save(branch1);

        var result = branchService.updateBranch(1L, branchDto1);

        assertNotNull(result);
        assertEquals(branchDto1, result);
    }

    @Test
    void deleteBranch() {
        when(branchValidation.findBranchValidator(1L)).thenReturn(branch1);
        branchService.deleteBranch(1L);

        verify(branchRepository).delete(branch1);
    }
}