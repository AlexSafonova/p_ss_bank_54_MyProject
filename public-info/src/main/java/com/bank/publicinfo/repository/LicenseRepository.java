package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    List<License> findAllLicenseByBankDetailsId(Long bank_details_id);
}
