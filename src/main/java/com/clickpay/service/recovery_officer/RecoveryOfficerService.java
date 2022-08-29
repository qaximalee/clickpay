package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user.User;
import com.clickpay.service.recovery_officer.officer.IOfficerService;
import com.clickpay.utils.Message;
import com.clickpay.utils.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Message findAllOfficerByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching list of officers.");
        return new Message()
                .setData(officerService.findAllOfficersByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("City list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateOfficer(OfficerRequest request, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Updating officer with provided request data.");
        return new Message()
                .setData(officerService.updateOfficer(request, user))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Officer "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }
}
