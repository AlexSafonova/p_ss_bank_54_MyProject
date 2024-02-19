package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findAllCertificatesByBankDetailsId(Long bank_details_id);
}
