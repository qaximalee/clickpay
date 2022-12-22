package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.PaginatedUserCollectionRequest;
import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserCollectionService {
    @Transactional
    UserCollection createUserCollection(UserCollectionRequest requestDto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    @Transactional
    UserCollection updateUserCollectionStatus(String status, Long collectionId, Long customerId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

//    List<CustomerResponse> getCustomersByFilter(PaginatedUserCollectionRequest request, User user);

    @Transactional
    UserCollection getUserCollectionById(Long collectionId, Long customerId, User user) throws EntityNotFoundException;

    @Transactional
    UserCollection save(UserCollection userCollection) throws BadRequestException, EntityNotSavedException;

    @Transactional
    UserCollection delete(Long collectionId, Long customerId, User user) throws EntityNotFoundException, BadRequestException, EntityNotSavedException;
}
