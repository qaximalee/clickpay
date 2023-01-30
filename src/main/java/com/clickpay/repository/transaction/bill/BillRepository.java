package com.clickpay.repository.transaction.bill;

import com.clickpay.model.transaction.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    Bill findByBillNumberAndIsDeleted(Long billNo, boolean b);
}
