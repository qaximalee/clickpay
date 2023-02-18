package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.area_allocation.AreaAllocationRequest;
import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.dto.recovery_officer.officer.OfficerUpdateRequest;
import com.clickpay.dto.recovery_officer.officer.PaginatedOfficerRequest;
import com.clickpay.dto.recovery_officer.officer.PaginatedOfficerResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface IRecoveryOfficerService {

    /**
     * For CRUD operations of officer
     * */
    Message createOfficer(OfficerRequest request, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message findOfficerById(Long id) throws BadRequestException, EntityNotFoundException;

    Message<PaginatedOfficerResponse> findAllOfficerByUserId(String status, Integer pageNo, Integer pageSize, Long userId) throws EntityNotFoundException, BadRequestException;

    Message updateOfficer(OfficerUpdateRequest request, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message findAreaAllocatedByUserId(Long userId, User user) throws EntityNotFoundException;

    Message createOfficerArea(AreaAllocationRequest request, User loggedInUser)
            throws EntityNotFoundException;

    Message getAllCustomerDropdownForCollectionHistory(User user) throws EntityNotFoundException;

    Message getUserCollectionByCustomerId(Long customerId, Integer pageNo, Integer pageSize, User user) throws EntityNotFoundException;

    Message getAllOfficerByOfficerIdOrAdmin(User user) throws EntityNotFoundException;

    Message getUserCollectionByRecoveryOfficer(int pageNo, int pageSize, User user) throws EntityNotFoundException;
}
