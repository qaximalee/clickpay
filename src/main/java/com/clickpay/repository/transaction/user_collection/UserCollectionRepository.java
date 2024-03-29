package com.clickpay.repository.transaction.user_collection;

import com.clickpay.model.transaction.UserCollection;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    Page<UserCollection> findByCustomer_IdAndIsDeleted(Long customerId, boolean b, Pageable paging);

    UserCollection findByCustomer_IdAndMonthAndYearAndIsDeleted(Long customerId, Months month, int year, boolean b);

    boolean existsByMonthAndYearAndCustomer_IdAndIsDeleted(Months month, Integer year, Long customerId, boolean isDeleted);

    boolean existsByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_ConnectionType_IdAndBillsCreator_Id(Months month, int year, UserCollectionStatus paid, PaymentType monthly, Long connectionTypeId, Long billCreatorId);

    boolean existsByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_SubLocality_IdAndCustomer_ConnectionType_IdAndBillsCreator_Id(Months month, int year, UserCollectionStatus paid, PaymentType monthly, Long subLocalityId, Long connectionTypeId, Long billCreatorId);

    List<UserCollection> findAllByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_ConnectionType_IdAndBillsCreator_Id(Months month, int year, UserCollectionStatus paid, PaymentType monthly, Long connectionTypeId, Long billCreatorId);

    List<UserCollection> findAllByMonthAndYearAndCollectionStatusAndPaymentTypeAndCustomer_SubLocality_IdAndCustomer_ConnectionType_IdAndBillsCreator_Id(Months month, int year, UserCollectionStatus paid, PaymentType monthly, Long subLocalityId, Long connectionTypeId, Long billCreatorId);

    Page<UserCollection> findByModifiedByAndIsDeleted(long officerId, boolean b, Pageable paging);
}
