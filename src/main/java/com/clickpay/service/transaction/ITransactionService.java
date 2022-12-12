package com.clickpay.service.transaction;

import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface ITransactionService {
    Message<UserCollection> createUserCollection(UserCollectionRequest requestDto, User user) throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;
}
