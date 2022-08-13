package com.clickpay.repository.recovery_officer;

import com.clickpay.model.recovery_officer.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {
}
