package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Bank_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Bank_detailsRepository extends JpaRepository<Bank_details, Long> {
}
