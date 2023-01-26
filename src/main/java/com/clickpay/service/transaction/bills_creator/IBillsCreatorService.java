package com.clickpay.service.transaction.bills_creator;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.bills_creator.BillsCreator;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBillsCreatorService {

    @Transactional
    BillsCreator createBillsCreator(BillsCreatorRequest request, User user) throws BadRequestException, EntityNotFoundException;

    @Transactional(readOnly = true)
    Page<BillsCreator> getAllBillCreatorsByUserId(Long userId,int pageNo, int pageSize) throws EntityNotFoundException;

    @Transactional
    BillsCreator deleteBillCreators(Long billCreatorId, User user) throws EntityNotFoundException;
}
