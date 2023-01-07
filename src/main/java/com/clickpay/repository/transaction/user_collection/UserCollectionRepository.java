package com.clickpay.repository.transaction.user_collection;

import com.clickpay.model.transaction.UserCollection;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
    //boolean existsByMonthAndYearAndPaymentTypeAndCustomer_Id(Months month, Integer year, PaymentType paymentType, Long customerId);

    boolean existsByMonthAndYearAndCollectionStatusAndCustomer_Id(Months month, Integer year, UserCollectionStatus collectionStatus, Long customerId);

    UserCollection deleteByIdAndCustomer_Id(Long collectionId, Long customerId);

    boolean existsByMonthAndYearAndPaymentTypeAndCustomer_IdAndIsDeleted(Months month, Integer year, PaymentType paymentType, Long customerId, boolean isDeleted);

    //List<UserCollection> findByBill_BillNumber(Long billNo);

    List<UserCollection> findByBill_BillNumberAndIsDeleted(Long billNo, boolean isDeleted);

    Optional<UserCollection> findByIdAndIsDeleted(Long collectionId, boolean isDeleted);

//    @Query(value = "",
//    nativeQuery = true)
//    List<Object[]> findCustomersWithFilter(String subLocality, String customerStatus, String userCollectionStatus, String connectionType, String searchInput, Long usrId);
}
