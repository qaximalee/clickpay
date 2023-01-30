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
import com.clickpay.model.user_profile.Customer;
import com.clickpay.service.transaction.bills_creator.IBillsCreatorService;
import com.clickpay.service.transaction.user_collection.IUserCollectionService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService implements ITransactionService{

    private final IUserCollectionService userCollectionService;
    private final IBillsCreatorService billsCreatorService;
    private final ICustomerService customerService;

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

    /**
    * CRUD For Bills Creator
    * */
    @Override
    public Message<BillsCreator> createBillsCreator(BillsCreatorRequest requestDto, User user) throws EntityNotFoundException, EntityAlreadyExistException, BadRequestException, EntityNotSavedException {
        log.info("Creating bills creator by requested data.");
        List<Customer> customers;
        if (requestDto.getSubLocality() == null){
            customers = customerService.findAllCustomerByIdAndConnectionTypeId(user.getId(), requestDto.getConnectionType());
        }else {
            customers = customerService.findAllCustomerByIdAndConnectionTypeIdAndSubLocalityId(user.getId(),
                    requestDto.getConnectionType(), requestDto.getSubLocality());
        }

        double totalAmount = 0;

        for(Customer customer : customers){
            totalAmount = totalAmount + customer.getAmount();
            UserCollectionRequest userCollectionRequest = new UserCollectionRequest();
            userCollectionRequest.setCustomerId(customer.getId());
            userCollectionRequest.setCollectionStatus(UserCollectionStatus.UNPAID.name());
            userCollectionRequest.setAmount(customer.getAmount());
            userCollectionRequest.setMonth(requestDto.getMonth());
            userCollectionRequest.setYear(requestDto.getYear());
            userCollectionRequest.setPaymentType(PaymentType.MONTHLY.name());
            createUserCollection(userCollectionRequest,user);
        }

        requestDto.setAmount(totalAmount);
        requestDto.setNoOfUsers(customers.size());

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
    public Message<BillsCreator> deleteBillCreator(Long billCreatorId, User user) throws EntityNotFoundException, EntityNotSavedException {
        log.info("Deleting bills creator.");

        return new Message<BillsCreator>()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Bills Creator Deleted Successfully.")
                .setData(billsCreatorService.deleteBillCreators(billCreatorId,user));
    }

}
