package com.clickpay.service.transaction;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorRequest;
import com.clickpay.dto.transaction.bills_creator.PaginatedBillsCreatorResponse;
import com.clickpay.dto.transaction.user_collection.UserCollectionRequest;
import com.clickpay.dto.transaction.user_collection.UserCollectionResponse;
import com.clickpay.dto.transaction.user_collection.UserCollectionStatusUpdateAsPaidDTO;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.BillsCreator;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface ITransactionService {
    Message<UserCollectionResponse> createUserCollection(UserCollectionRequest requestDto, User user) throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<UserCollectionResponse> getUserCollection(Long collectionId, Long customerId, User user) throws EntityNotFoundException;

    Message<UserCollectionResponse> deleteUserCollection(Long collectionId, Long customerId, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<String> updateUserCollectionStatusAsPaid(UserCollectionStatusUpdateAsPaidDTO updateDTO, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<String> updateUserCollectionStatusAsUnPaid(Long billNo, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<BillsCreator> createBillsCreator(BillsCreatorRequest requestDto, User user) throws EntityNotFoundException, EntityAlreadyExistException, BadRequestException, EntityNotSavedException;

    Message<PaginatedBillsCreatorResponse> getAllBillCreatorsByUserId(Long userId, int pageNo, int pageSize) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<BillsCreator> deleteBillCreator(Long billCreatorId, User user) throws EntityNotFoundException, EntityNotSavedException;

    // find of customers in user collection
    //Message<PaginatedUserCollectionResponse> getAllUserOfCollections(PaginatedUserCollectionRequest request, User user);
}
