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
import com.clickpay.model.user_profile.Customer;
import com.clickpay.service.transaction.bills_creator.IBillsCreatorService;
import com.clickpay.service.transaction.user_collection.IUserCollectionService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.enums.Months;
import com.clickpay.utils.enums.PaymentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService implements ITransactionService{

    private final IUserCollectionService userCollectionService;
    private final IBillsCreatorService billsCreatorService;

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
    public Message<UserCollection> getUserCollection(Long collectionId, Long customerId, User user) throws EntityNotFoundException {
        log.info("Fetching user collection by collection Id "+collectionId+" .");
//        UserCollectionResponse response =
//                UserCollectionResponse.fromUserCollection(
//                        userCollectionService.getUserCollectionById(collectionId,customerId,user));
        return new Message<UserCollection>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Fetched Successfully.")
                .setData(userCollectionService.getUserCollectionById(collectionId,customerId,user));
    }

    @Override
    public Message<PaginatedUserCollectionsResponse> getUserCollectionByCustomerId(Long customerId, int pageNo, int pageSize) throws EntityNotFoundException {
        log.info("Fetching user collection by customer Id : "+customerId+" .");
        Page<UserCollection> userCollections = userCollectionService.getUserCollectionByCustomerId(customerId,pageNo,pageSize);
        PaginatedUserCollectionsResponse response = new PaginatedUserCollectionsResponse();
        response.setUserCollections(UserCollectionResponse.fromUserCollectionList(userCollections.getContent()));
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalRows(userCollections.getTotalElements());
        response.setNoOfPages(userCollections.getTotalPages());

        return new Message<PaginatedUserCollectionsResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collections By Customer Id Fetched Successfully.")
                .setData(response);
    }

    @Override
    public Message<UserCollectionResponse> deleteUserCollection(Long collectionId, Long customerId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Deleting user collection by collection Id "+collectionId+" .");
        UserCollectionResponse response =
                UserCollectionResponse.fromUserCollection(
                        userCollectionService.delete(collectionId,customerId,user));
        return new Message<UserCollectionResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Deleted Successfully.")
                .setData(response);
    }

    @Override
    public Message<String> updateUserCollectionStatusAsPaid(UserCollectionStatusUpdateAsPaidDTO updateDTO, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Updating user collection by collection status.");

        return new Message<String>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Status Updated Successfully.")
                .setData(userCollectionService.updateUserCollectionStatusAsPaid(updateDTO,user));
    }

    @Override
    public Message<String> updateUserCollectionStatusAsUnPaid(Long billNo, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Updating user collection by collection status.");

        return new Message<String>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("User Collection Status Updated Successfully.")
                .setData(userCollectionService.updateUserCollectionStatusAsUnPaid(billNo,user));
    }

    /**
    * CRUD For Bills Creator
    * */
    @Transactional
    @Override
    public Message<BillsCreator> createBillsCreator(BillsCreatorCreateRequest requestDto, User user) throws EntityNotFoundException, EntityAlreadyExistException, BadRequestException, EntityNotSavedException {
        log.info("Creating bills creator by requested data.");

        BillsCreator response = billsCreatorService.createBillsCreator(requestDto,user);
        return new Message<BillsCreator>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Bills Creator Created Successfully.")
                .setData(response);
    }

    @Override
    public Message<PaginatedBillsCreatorResponse> getAllBillCreatorsByUserId(Long userId, int pageNo, int pageSize) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Fetching bills creator by user id.");

        Page<BillsCreator> billsCreators = billsCreatorService.getAllBillCreatorsByUserId(userId,pageNo,pageSize);
        PaginatedBillsCreatorResponse response = new PaginatedBillsCreatorResponse();
        response.setBillsCreators(billsCreators.getContent());
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setNoOfPages(billsCreators.getTotalPages());
        response.setTotalRows(billsCreators.getTotalElements());

        return new Message<PaginatedBillsCreatorResponse>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Bills Creator by user id Fetched Successfully.")
                .setData(response);
    }

    @Override
    public Message<BillsCreator> deleteBillCreator(BillsCreatorDeleteRequest request, User user) throws EntityNotFoundException, BadRequestException, EntityNotSavedException {
        log.info("Deleting bills creator.");

        // check any bill creator collection paid or not
        if (userCollectionService.checkBillCreatorCollectionPaid(
                request.getBillCreatorId(),
                request.getConnectionType(),
                request.getSubLocality(),
                Months.of(request.getMonth()).name(),
                request.getYear()
        )){
            log.info("Some User Collection Are Paid So Not Permit to Delete Bill Creator.");
            throw new BadRequestException("Some User Collection Are Paid So Not Permit to Delete Bill Creator.");
        }

        // delete all user collections which created by bill creator
        userCollectionService.deleteBillCreatorUserCollections(
                request.getBillCreatorId(),
                request.getConnectionType(),
                request.getSubLocality(),
                Months.of(request.getMonth()).name(),
                request.getYear(),
                user
        );

        return new Message<BillsCreator>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Bills Creator Deleted Successfully.")
                .setData(billsCreatorService.deleteBillCreators(request.getBillCreatorId(),user));
    }

}
