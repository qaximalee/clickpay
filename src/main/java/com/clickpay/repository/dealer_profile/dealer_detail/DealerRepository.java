package com.clickpay.repository.dealer_profile.dealer_detail;

import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findAllByCreatedBy(Long userId);

    boolean existsByDealerID(String dealerId);

    Optional<Dealer> findByIdAndIsDeleted(Long id, boolean isDeleted);


    Page<Dealer> findAllByCreatedByAndIsDeleted( Long userId, boolean isDeleted, Pageable pageable);

    @Query(value = "SELECT * FROM dealer" +
            "  where (locality_id = :localityId OR :localityId is null)" +
            "  AND (company_id = :companyId OR :companyId is null)" +
            "  AND (status = :status OR :status is null)" +
            "  AND created_by = :userId AND is_deleted = :isDeleted ", nativeQuery = true)
    Page<Dealer> findAllByCreatedByAndIsDeletedAndByFilters(Long companyId, Long localityId, String status, Long userId, Boolean isDeleted, Pageable pageable);
}
