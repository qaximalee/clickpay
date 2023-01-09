package com.clickpay.repository.company;

import com.clickpay.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByCreatedBy(Long userId);

    //Optional<Company> findByIdAndActive(Long id, boolean isActive);

    //List<Company> findAllByCreatedByAndActive(Long userId, boolean b);

    List<Company> findAllByCreatedByAndActiveAndIsDeleted(Long userId, boolean active, boolean isDeleted);

    Optional<Company> findByIdAndActiveAndIsDeleted(Long id, boolean active, boolean isDeleted);
}
