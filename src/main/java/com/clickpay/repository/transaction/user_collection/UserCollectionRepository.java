package com.clickpay.repository.transaction.user_collection;

import com.clickpay.model.transaction.UserCollection;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
    boolean existsByMonthAndYearAndPaymentTypeAndCustomer_Id(Months month, Integer year, PaymentType paymentType, Long customerId);

    boolean existsByMonthAndYearAndCollectionStatusAndCustomer_Id(Months month, Integer year, UserCollectionStatus collectionStatus, Long customerId);

    UserCollection deleteByIdAndCustomer_Id(Long collectionId, Long customerId);
}
