package com.clickpay.service.transaction;

import com.clickpay.dto.transaction.UserCollectionRequest;
import com.clickpay.dto.transaction.UserCollectionResponse;
import com.clickpay.dto.transaction.UserCollectionStatusUpdateAsPaidDTO;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
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

    // find of customers in user collection
//    @Override
//    public Message<PaginatedUserCollectionResponse> getAllUserOfCollections(PaginatedUserCollectionRequest request, User user){
//        log.info("Fetching customers by finding fields.");
//
//        List<CustomerResponse> customers = userCollectionService.getCustomersByFilter(request,user);
//        PaginatedUserCollectionResponse response = PaginatedUserCollectionResponse.builder()
//                .customersList(customers)
//                .pageNo(request.getPageNo())
//                .pageSize(request.getPageSize())
//                .noOfPages().build();
//
//        return new Message<UserCollectionResponse>()
//                .setStatus(HttpStatus.OK.value())
//                .setCode(HttpStatus.OK.toString())
//                .setMessage("Customers by User Collections Fetched Successfully.")
//                .setData(response);
//    }


}
