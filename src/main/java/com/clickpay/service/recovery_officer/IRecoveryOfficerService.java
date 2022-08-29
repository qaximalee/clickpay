package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
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

    Message findAllOfficerByUserId(Long userId) throws EntityNotFoundException;

    Message updateOfficer(OfficerRequest request, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;
}
