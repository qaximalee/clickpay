package com.clickpay.service.dealer_profile.dealer_detail;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDealerDetailService {
    @Transactional(readOnly = true)
    boolean isExistsByDealerID(String dealerId) throws EntityAlreadyExistException;

    @Transactional
    Dealer save(Dealer dealer) throws EntityNotSavedException, BadRequestException;

    @Transactional(readOnly = true)
    Dealer findById(Long id) throws BadRequestException, EntityNotFoundException;

    @Transactional(readOnly = true)
    List<Dealer> findAllDealerByUserId(Long userId, Boolean isDeleted) throws EntityNotFoundException;
}
