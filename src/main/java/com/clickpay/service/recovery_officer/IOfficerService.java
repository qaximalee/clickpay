package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.dto.recovery_officer.officer.OfficerResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.model.user.User;
import org.springframework.transaction.annotation.Transactional;

public interface IOfficerService {
    OfficerResponse createOfficer(OfficerRequest request, User user) throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    @Transactional
    Officer save(Officer officer) throws BadRequestException, EntityNotSavedException;
}
