package com.clickpay.repository.user_profile;

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
            "c.discount, c.amount FROM customer AS c " +
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

    @Query(value = "SELECT DISTINCT c.id,c.internet_id,c.name,c.address,c.mobile,c.connection_type_id,c.installation_date,c.packages_id,c.status,c.discount,c.user_id \n" +
            "FROM customer AS c\n" +
            "INNER JOIN user_collection AS uc ON uc.customer_id = c.id\n" +
            "WHERE ( (c.created_by =:userId ) AND\n" +
            "(c.sub_locality_id =:subLocalityId or :subLocalityId is NULL) AND\n" +
            "(c.connection_type_id =:connectionTypeId or :connectionTypeId is NULL) AND \n" +
            "(c.status =:customerStatus or :customerStatus is NULL) AND\n" +
            "(uc.collection_status =:userCollectionStatus or :userCollectionStatus is NULL) AND \n" +
            " ( :searchInput is NULL OR c.internet_id like :searchInput OR c.name like :searchInput OR c.address like :searchInput OR c.mobile like :searchInput)) ",
    nativeQuery = true)
    List<Object[]> findCustomersByUserCollectionsWithFilter(Long subLocalityId, String customerStatus, String userCollectionStatus, Long connectionTypeId, String searchInput, Long userId);

    List<Customer> findAllByCreatedByAndConnectionType_Id(Long userId, Long connectionTypeId);

    List<Customer> findAllByCreatedByAndConnectionType_IdAndSubLocality_Id(Long userId, Long connectionTypeId, Long subLocalityId);

    // TODO fetch customer with admin or recovery officer id
    @Query(value = "SELECT c.id, c.name, c.sub_locality_id FROM customer as c" +
            "  INNER JOIN officer_area oa ON oa.sub_locality_id = c.sub_locality_id" +
            "  INNER JOIN officer o ON o.user_id = oa.user_id AND oa.created_by = o.created_by" +
            "  where oa.user_id = :userId AND o.status = 'ACTIVE'", nativeQuery = true)
    List<Object[]> findAllUsersWithRespectToTheRecoveryOfficer(long userId);
}
