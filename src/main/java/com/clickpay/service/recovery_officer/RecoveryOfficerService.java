package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationRequest;
import com.clickpay.dto.recovery_officer.officer.*;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.model.user.User;
import com.clickpay.service.recovery_officer.area_allocation.IAreaAllocationService;
import com.clickpay.service.recovery_officer.officer.IOfficerService;
import com.clickpay.service.transaction.ITransactionService;
import com.clickpay.service.user_profile.customer.ICustomerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecoveryOfficerService implements IRecoveryOfficerService{

    private final IOfficerService officerService;
    private final IAreaAllocationService areaAllocationService;
    private final ICustomerService customerService;
    private final ITransactionService transactionService;

    /**
     * CRUD operations for officer
     */
    @Override
    public Message createOfficer(OfficerRequest request, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Creating officer by provided request data.");
        return new Message()
                .setData(officerService.createOfficer(request, user))
                .setMessage("Officer: " + request.getName() + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setCode(HttpStatus.CREATED.toString())
                .setStatus(HttpStatus.CREATED.value());
    }

    @Override
    public Message findOfficerById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Fetching officer by id: "+id);
        return new Message()
                .setData(officerService.findById(id))
                .setMessage("Officer " + ResponseMessage.FETCHED_MESSAGE_SUCCESS)
                .setCode(HttpStatus.OK.toString())
                .setStatus(HttpStatus.OK.value());
    }

    @Override
    public Message<PaginatedOfficerResponse> findAllOfficerByUserId(String status, Integer pageNo, Integer pageSize, Long userId) throws EntityNotFoundException, BadRequestException {
        log.info("Fetching list of officers.");
        Page<Officer> officerResponses =  officerService.findAllOfficersByUserId(status, pageNo, pageSize, userId);

        PaginatedOfficerResponse response = PaginatedOfficerResponse.builder()
                .officers(OfficerResponse.fromListOfOfficer(officerResponses.getContent()))
                .pageNo(pageNo)
                .pageSize(pageSize)
                .noOfPages(officerResponses.getTotalPages())
                .totalRows(officerResponses.getTotalElements())
                .build();

        return new Message<PaginatedOfficerResponse>()
                .setData(response)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Officer list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateOfficer(OfficerUpdateRequest request, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {
        log.info("Updating officer with provided request data.");
        return new Message()
                .setData(officerService.updateOfficer(request, user))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Officer "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findAreaAllocatedByUserId(Long userId, User user) throws EntityNotFoundException {
        log.info("Fetching all officer's area allocation selected and unselected data.");
        return new Message()
                .setData(areaAllocationService.getAllAreaAllocationsByUserId(userId, user))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Area allocation: " + ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message createOfficerArea(AreaAllocationRequest request, User loggedInUser)
            throws EntityNotFoundException {
        log.info("Officer area allocation process started.");
        areaAllocationService.createOfficerArea(request, loggedInUser);
        return new Message()
                .setMessage("Area allocation for user id: " + request.getUserId() + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setCode(HttpStatus.CREATED.toString())
                .setStatus(HttpStatus.CREATED.value());
    }

    @Override
    public Message getUserCollectionByCustomerId(Long customerId, Integer pageNo, Integer pageSize, User user) throws EntityNotFoundException {
        log.info("Fetching all user collection for a customer.");
        return transactionService.getUserCollectionByCustomerId(customerId, pageNo, pageSize);
    }

    @Override
    public Message getAllOfficerByOfficerIdOrAdmin(User user) throws EntityNotFoundException {
        log.info("Fetching officer(s) by logged in user.");
        return new Message()
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Officer data for user: "+ user.getUsername() + ResponseMessage.FETCHED_MESSAGE_SUCCESS)
                .setData(officerService.getAllUsersWithRespectToTheAdminOrARecoveryOfficerId(user));
    }

    @Override
    public Message getUserCollectionByRecoveryOfficer(int pageNo, int pageSize, User user) throws EntityNotFoundException {
        log.info("Fetching all user collection for a customer.");
        return transactionService.getUserCollectionByReceivingUser(pageNo, pageSize, user);
    }

    @Override
    public Message getAllCustomerDropdownForCollectionHistory(User user) throws EntityNotFoundException {
        log.info("Customer dropdown data from recovery officer by officer allocation data.");
        return new Message()
                .setMessage("Customer dropdown data for user: "+ user.getUsername() + ResponseMessage.FETCHED_MESSAGE_SUCCESS)
                .setData(customerService.getAllUsersWithRespectToTheRecoveryOfficer(user))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString());
    }
}
