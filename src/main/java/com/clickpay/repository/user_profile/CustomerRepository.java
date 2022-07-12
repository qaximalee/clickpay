package com.clickpay.repository.user_profile;

import com.clickpay.model.user_profile.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByInternetIdAndCreatedBy(String internetId, Long id);
}
