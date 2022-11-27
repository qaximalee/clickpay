package com.clickpay.repository.dealer_profile.dealer_detail;

import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findAllByCreatedBy(Long userId);

    boolean existsByDealerID(String dealerId);

    Optional<Dealer> findByIdAndIsDeleted(Long id, boolean isDeleted);

    boolean existsByDealerIDAndIsDeleted(String dealerId, boolean isDeleted);

    Page<Dealer> findAllByCreatedByAndIsDeleted(Long userId, boolean isDeleted, Pageable pageable);

    Page<Dealer> findAllByCreatedByAndIsDeletedAndCompany_NameAndLocality_NameAndStatus(Long userId, Boolean isDeleted, String company, String locality, String status, Pageable pageable);
}
