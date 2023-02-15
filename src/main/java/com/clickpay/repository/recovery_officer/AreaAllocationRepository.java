package com.clickpay.repository.recovery_officer;

import com.clickpay.model.recovery_officer.OfficerArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaAllocationRepository extends JpaRepository<OfficerArea, Long> {
    List<OfficerArea> findAllByUserId(Long userId);
}
