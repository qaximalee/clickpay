package com.clickpay.repository.user_profile;

import com.clickpay.model.user_profile.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    //List<Package> findAllByCreatedBy(Long userId);

    List<Package> findAllByCreatedByAndIsDeleted(Long userId, boolean isDeleted);

    Optional<Package> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
