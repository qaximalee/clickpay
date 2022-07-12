package com.clickpay.service.user_profile;

import com.clickpay.dto.user_profile.customer.CreateCustomerRequest;
import com.clickpay.dto.user_profile.packages.PackageRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface IUserProfileService {

    Message findCompanyById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createCompany(String name, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    Message findAllCompaniesByUserId(Long userId) throws EntityNotFoundException;

    Message updateCompany(Long id, String name, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message findBoxMediaById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createBoxMedia(String boxNumber, String nearbyLocation, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    Message findAllBoxMediaByUserId(Long userId) throws EntityNotFoundException;

    Message updateBoxMedia(Long id, String boxNumber, String nearbyLocation, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message findConnectionTypeById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createConnectionType(String type, User user)
            throws EntityNotSavedException, BadRequestException;

    Message findAllConnectionTypeByUserId(Long userId) throws EntityNotFoundException;

    Message updateConnectionType(Long id, String type, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message findPackageById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createPackage(PackageRequest packageRequest, User user)
            throws EntityNotSavedException, BadRequestException, EntityNotFoundException;

    Message findAllPackageByUserId(Long userId) throws EntityNotFoundException;

    Message updatePackage(PackageRequest packageRequest, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message createCustomer(CreateCustomerRequest dto, User user) throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException;
}
