package com.clickpay.service.dealer_profile;

import com.clickpay.dto.dealer_profile.dealer_detail.DealerDetailRequest;
import com.clickpay.dto.dealer_profile.dealer_detail.PaginatedDealerRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface IDealerProfileService {

    Message createDealer(DealerDetailRequest dealerDetailRequest, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message findDealerById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllDealerByUserId(PaginatedDealerRequest dto, Long userId) throws EntityNotFoundException;

    Message updateDealer(DealerDetailRequest dealerDetailRequest, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<Dealer> updateDealerStatus(Long dealerId, String status, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<Dealer> deleteDealer(Long dealerId, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException;
}
