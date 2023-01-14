package com.clickpay.service.transaction.bills_creator;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.bills_creator.BillsCreator;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;
import org.springframework.transaction.annotation.Transactional;

public interface IBillsCreatorService {

    @Transactional
    BillsCreator createBillsCreator(BillsCreatorRequest request, User user) throws BadRequestException, EntityNotFoundException;
}
