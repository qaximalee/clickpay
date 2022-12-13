package com.clickpay.service.transaction;

import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.dto.transaction.UserCollectionResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.service.transaction.user_collection.IUserCollectionService;
import com.clickpay.utils.Message;
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
    public Message<UserCollectionResponse> createUserCollection(UserCollectionRequest requestDto, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Creating user collection by requested data.");
        UserCollectionResponse response =
                UserCollectionResponse.fromUserCollection(
                        userCollectionService.createUserCollection(requestDto, user));
        return new Message<UserCollectionResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Created Successfully.")
                .setData(response);
    }

    @Override
    public Message<UserCollectionResponse> getUserCollection(Long collectionId, Long customerId, User user) throws EntityNotFoundException {
        log.info("Fetching user collection by collection Id "+collectionId+" .");
        UserCollectionResponse response =
                UserCollectionResponse.fromUserCollection(
                        userCollectionService.getUserCollectionById(collectionId,customerId,user));
        return new Message<UserCollectionResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Fetched Successfully.")
                .setData(response);
    }

    @Override
    public Message<UserCollection> deleteUserCollection(Long collectionId, Long customerId, User user)
    {
        log.info("Deleting user collection by collection Id "+collectionId+" .");
        return new Message<UserCollection>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Deleted Successfully.")
                .setData(userCollectionService.delete(collectionId,customerId,user));
    }

    @Override
    public Message<UserCollectionResponse> updateUserCollectionStatus(String status, Long collectionId, Long customerId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Updating user collection by collection status.");

        UserCollectionResponse response =
                UserCollectionResponse.fromUserCollection(
                        userCollectionService.updateUserCollectionStatus(status,collectionId,customerId,user));
        return new Message<UserCollectionResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Status Updated Successfully.")
                .setData(response);
    }



}
