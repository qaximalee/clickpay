package com.clickpay.repository.user_profile;

import com.clickpay.model.user_profile.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByInternetIdAndCreatedBy(String internetId, Long id);

    List<Customer> findAllByCreatedBy(Long userId);

    @Query(value = "SELECT c.id, c.internet_id, c.name, c.address, c.mobile, ct.type, installation_date, p.package_name, c.status, " +
            "c.discount FROM customer AS c " +
            "INNER JOIN connection_type as ct ON ct.id = connection_type_id " +
            "INNER JOIN box_media as bm ON bm.id = box_media_id " +
            "INNER JOIN package as p ON p.id = packages_id " +
            "where (status = :status OR ct.id = :connectionTypeId OR bm.id = :boxMediaId OR p.id = :packageId " +
            "OR c.discount = :discount) AND c.created_by = :userId ORDER BY name, internet_id Asc", nativeQuery = true)
    List<Object[]> getCustomerByFiltration(String status, Long connectionTypeId, Long boxMediaId, Long packageId, String discount, Long userId);


}
