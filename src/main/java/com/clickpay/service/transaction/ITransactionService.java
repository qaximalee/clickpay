package com.clickpay.service.transaction;

import com.clickpay.dto.transaction.bills_creator.BillsCreatorCreateRequest;
import com.clickpay.dto.transaction.bills_creator.BillsCreatorDeleteRequest;
import com.clickpay.dto.transaction.bills_creator.PaginatedBillsCreatorResponse;
import com.clickpay.dto.transaction.user_collection.PaginatedUserCollectionsResponse;
import com.clickpay.dto.transaction.user_collection.UserCollectionRequest;
import com.clickpay.dto.transaction.user_collection.UserCollectionResponse;
import com.clickpay.dto.transaction.user_collection.UserCollectionStatusUpdateAsPaidDTO;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.BillsCreator;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;
import org.springframework.transaction.annotation.Transactional;

public interface ITransactionService {
    Message<UserCollectionResponse> createUserCollection(UserCollectionRequest requestDto, User user) throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<UserCollection> getUserCollection(Long collectionId, Long customerId, User user) throws EntityNotFoundException;

    Message<PaginatedUserCollectionsResponse> getUserCollectionByCustomerId(Long customerId, int pageNo, int pageSize) throws EntityNotFoundException;

    Message<UserCollectionResponse> deleteUserCollection(Long collectionId, Long customerId, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<String> updateUserCollectionStatusAsPaid(UserCollectionStatusUpdateAsPaidDTO updateDTO, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<String> updateUserCollectionStatusAsUnPaid(Long billNo, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    @Transactional
    Message<BillsCreator> createBillsCreator(BillsCreatorCreateRequest requestDto, User user) throws EntityNotFoundException, EntityAlreadyExistException, BadRequestException, EntityNotSavedException;

    Message<PaginatedBillsCreatorResponse> getAllBillCreatorsByUserId(Long userId, int pageNo, int pageSize) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message<BillsCreator> deleteBillCreator(BillsCreatorDeleteRequest request, User user) throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    @Transactional(readOnly = true)
    Message<PaginatedUserCollectionsResponse> getUserCollectionByReceivingUser(int pageNo, int pageSize, User user) throws EntityNotFoundException;
}
