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

//    @Query(value = "SELECT c.id,c.internet_id,c.name,c.address,c.mobile,c.connection_type_id,c.installation_date,c.packages_id,c.status,c.discount,c.user_id " +
//            "  FROM customer AS c " +
//            "  INNER JOIN user_collection AS uc ON uc.customer_id = c.id " +
//            "  WHERE ( (c.created_by =:userId ) AND " +
//            "  (c.sub_locality_id =:subLocality or :subLocality is NULL) AND " +
//            "  (c.connection_type_id =:connectionType or :connectionType is NULL) AND " +
//            "  (c.status =:customerStatus or :customerStatus is NULL) AND " +
//            "  (uc.collection_status =:userCollectionStatus or :userCollectionStatus is NULL) AND " +
//            "  ( (:searchInput is NULL) OR " +
//            "  ( c.internet_id like '%'+:searchInput+'%' ) OR " +/*or :searchInput is NULL*/
//            "  ( c.name like '%'+:searchInput+'%' ) OR " +/*or :searchInput is NULL*/
//            "  ( c.address like '%'+:searchInput+'%' ) OR " +/*or :searchInput is NULL*/
//            "  ( c.mobile like '%'+:searchInput+'%' )  ) ) ",/*or :searchInput is NULL*/
//            nativeQuery = true)
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
    Page<Object[]> findCustomersByUserCollectionsWithFilter(Long subLocalityId, String customerStatus, String userCollectionStatus, Long connectionTypeId, String searchInput, Long userId, Pageable pageable);

    List<Customer> findAllByCreatedByAndConnectionType_Id(Long userId, Long connectionTypeId);

    List<Customer> findAllByCreatedByAndConnectionType_IdAndSubLocality_Id(Long userId, Long connectionTypeId, Long subLocalityId);
}
