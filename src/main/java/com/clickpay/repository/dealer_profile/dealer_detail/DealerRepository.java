package com.clickpay.repository.dealer_profile.dealer_detail;

import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findAllByCreatedBy(Long userId);

    boolean existsByDealerID(String dealerId);
}
