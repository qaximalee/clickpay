package com.clickpay.service.user_profile;

import com.clickpay.dto.user_profile.customer.*;
import com.clickpay.dto.user_profile.packages.PackageRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.company.Company;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.model.user_profile.Package;
import com.clickpay.utils.Message;

import java.util.List;

public interface IUserProfileService {

    // Company CRUD
    Message findCompanyById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createCompany(String name, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException, EntityAlreadyExistException;

    Message findAllCompaniesByUserId(Long userId) throws EntityNotFoundException;

    Message updateCompany(Long id, String name, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException;

    Message<Company> deleteCompany(Long id, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException;

    // BoxMedia CRUD
    Message findBoxMediaById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createBoxMedia(String boxNumber, String nearbyLocation, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException, EntityAlreadyExistException;

    Message findAllBoxMediaByUserId(Long userId) throws EntityNotFoundException;

    Message updateBoxMedia(Long id, String boxNumber, String nearbyLocation, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException;

    Message<BoxMedia> deleteBoxMedia(Long id, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException;

    // ConnectionType CRUD
    Message findConnectionTypeById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createConnectionType(String type, User user)
            throws EntityNotSavedException, BadRequestException;

    Message findAllConnectionTypeByUserId(Long userId) throws EntityNotFoundException;

    Message updateConnectionType(Long id, String type, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    // Packages CRUD
    Message findPackageById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createPackage(PackageRequest packageRequest, User user)
            throws EntityNotSavedException, BadRequestException, EntityNotFoundException, EntityAlreadyExistException;

    Message findAllPackageByUserId(Long userId) throws EntityNotFoundException;

    Message findAllPackageByCompanyIdAndUserId(Long companyId, Long userId) throws EntityNotFoundException;

    Message updatePackage(PackageRequest packageRequest, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException;

    Message<Package> deletePackage(Long packageId, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException, EntityAlreadyExistException;

    // Customer CRUD
    Message createCustomer(CustomerRequest dto, User user) throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message updateCustomer(CustomerRequest dto, User user)
            throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message updateCustomerStatus(Long customerId, boolean status, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message findCustomerById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllCustomerByUserId(Long userId) throws EntityNotFoundException;

    Message findCustomerByFilter(CustomerFilterAndPaginationRequest customerFilterDTO, User user) throws EntityNotFoundException;

    Message<List<CustomerResponse>> getAllCustomersByUserCollections(CustomersInUserCollectionRequest request, User user) throws EntityNotFoundException;
}
