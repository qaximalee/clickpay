package com.clickpay.service.recovery_officer.officer;

import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.dto.recovery_officer.officer.OfficerResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOfficerService {
    @Transactional
    OfficerResponse createOfficer(OfficerRequest request, User user) throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException;

    @Transactional(readOnly = true)
    OfficerResponse findById(Long id) throws BadRequestException, EntityNotFoundException;

    @Transactional(readOnly = true)
    List<OfficerResponse> findAllOfficersByUserId(Long userId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    OfficerResponse updateOfficer(OfficerRequest request, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException;
}