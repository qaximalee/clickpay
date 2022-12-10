package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.UnpaidCollectionResponse;
import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import org.springframework.transaction.annotation.Transactional;

public interface IUserCollectionService {
    Message<UnpaidCollectionResponse> getUserUnpaidCollections(Long userId) throws EntityNotFoundException;

    @Transactional
    Message<UserCollection> createUserCollection(UserCollectionRequest requestDto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    @Transactional
    UserCollection save(UserCollection userCollection) throws BadRequestException, EntityNotSavedException;

  //  boolean existsByMonthOrYearOrTypeOfCustomer(String month, Integer year, PaymentType paymentType, Long customerId);

    boolean existsByMonthOrYearOrTypeOfCustomer(Months month, Integer year, PaymentType paymentType, Long customerId);
}
