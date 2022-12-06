package com.clickpay.repository.user_profile;

import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.model.user_profile.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "where ( status = :status OR :status is null ) " +
            "AND ( ct.id = :connectionTypeId OR :connectionTypeId is null )" +
            "AND ( bm.id = :boxMediaId OR :boxMediaId is null )" +
            "AND ( p.id = :packageId OR :packageId is null ) " +
            "AND ( c.discount = :discount OR :discount is null ) " +
            "AND ( c.created_by = :userId ) ORDER BY name, internet_id Asc", nativeQuery = true)
    Page<Object[]> getCustomerByFiltration(String status, Long connectionTypeId, Long boxMediaId, Long packageId, String discount, Long userId, Pageable pageable);

    Customer findByUser_Id(Long userId);
}
