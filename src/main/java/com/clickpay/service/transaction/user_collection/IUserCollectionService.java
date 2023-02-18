package com.clickpay.service.transaction.user_collection;

import com.clickpay.dto.transaction.user_collection.UserCollectionRequest;
import com.clickpay.dto.transaction.user_collection.UserCollectionStatusUpdateAsPaidDTO;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.transaction.UserCollection;
import com.clickpay.model.user.User;
import com.clickpay.utils.enums.Months;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface IUserCollectionService {
    @Transactional
    UserCollection createUserCollection(UserCollectionRequest requestDto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    @Transactional
    String updateUserCollectionStatusAsPaid(UserCollectionStatusUpdateAsPaidDTO updateDTO, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    @Transactional
    String updateUserCollectionStatusAsUnPaid(Long billNo, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    @Transactional
    UserCollection getUserCollectionById(Long collectionId, Long customerId, User user) throws EntityNotFoundException;

    @Transactional
    Page<UserCollection> getUserCollectionByCustomerId(Long customerId, int pageNo, int pageSize) throws EntityNotFoundException;

    @Transactional
    UserCollection getUserCollectionByCustomerIdAndMonthAndYear(Long customerId, Months month, int year) throws EntityNotFoundException;

    @Transactional
    List<UserCollection> getUserCollectionsByBillNumber(Long billNo, User user) throws EntityNotFoundException;

    @Transactional
    UserCollection save(UserCollection userCollection) throws BadRequestException, EntityNotSavedException;

    @Transactional
    UserCollection delete(Long collectionId, Long customerId, User user) throws EntityNotFoundException, BadRequestException, EntityNotSavedException;

    boolean checkBillCreatorCollectionPaid(Long billCreatorId, Long connectionTypeId, Long subLocalityId, String month, int year) throws BadRequestException;

    void deleteBillCreatorUserCollections(Long billCreatorId, Long connectionTypeId, Long subLocalityId, String month, int year, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Page<UserCollection> getUserCollectionByOfficerId(long officerId, int pageNo, int pageSize) throws EntityNotFoundException;
}
