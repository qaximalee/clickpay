package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.officer.*;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.model.user.User;
import com.clickpay.service.recovery_officer.officer.IOfficerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RecoveryOfficerService implements IRecoveryOfficerService{

    private final IOfficerService officerService;

    @Autowired
    public RecoveryOfficerService(IOfficerService officerService) {
        this.officerService = officerService;
    }

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
    public Message updateOfficer(OfficerUpdateRequest request, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Updating officer with provided request data.");
        return new Message()
                .setData(officerService.updateOfficer(request, user))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Officer "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }
}
