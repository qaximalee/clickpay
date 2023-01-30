package com.clickpay.service.transaction.bill;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.Bill;
import org.springframework.transaction.annotation.Transactional;

public interface IBillService {
    @Transactional
    Bill save(Bill userBill) throws BadRequestException, EntityNotSavedException;

    @Transactional(readOnly = true)
    Bill getBillByBillNumber(Long billNo) throws EntityNotFoundException;
}
