package com.clickpay.service.user_profile;

import com.clickpay.dto.packages.PackageRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface IUserProfileService {

    Message findCompanyById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createCompany(String name, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    Message findBoxMediaById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createBoxMedia(String boxNumber, String nearbyLocation, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    Message findConnectionTypeById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createConnectionType(String type, User user)
            throws EntityNotSavedException, BadRequestException;

    Message findPackageById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createPackage(PackageRequest packageRequest, User user)
            throws EntityNotSavedException, BadRequestException, EntityNotFoundException;
}
