package com.clickpay.service.transaction;

import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.service.transaction.user_collection.IUserCollectionService;
import com.clickpay.utils.Message;
import com.clickpay.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService implements ITransactionService{

    private final IUserCollectionService userCollectionService;

    @Override
    public Message<UserCollection> createUserCollection(UserCollectionRequest requestDto, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Creating user collection by requested data.");
        return new Message<UserCollection>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Created Successfully.")
                .setData(userCollectionService.createUserCollection(requestDto, user));
    }
}
