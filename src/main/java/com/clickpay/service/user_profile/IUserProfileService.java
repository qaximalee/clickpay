package com.clickpay.service.user_profile;

import com.clickpay.dto.user_profile.customer.PaginatedCustomersInUserCollectionRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationResponse;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.packages.PackageRequest;
import com.clickpay.errors.general.*;
import com.clickpay.model.company.Company;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.BoxMedia;
import com.clickpay.model.user_profile.Package;
import com.clickpay.utils.Message;

public interface IUserProfileService {

    // Company CRUD
    Message findCompanyById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createCompany(String name, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    Message findAllCompaniesByUserId(Long userId) throws EntityNotFoundException;

    Message updateCompany(Long id, String name, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message<Company> deleteCompany(Long id, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    // BoxMedia CRUD
    Message findBoxMediaById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createBoxMedia(String boxNumber, String nearbyLocation, User user)
            throws EntityNotFoundException, EntityNotSavedException, BadRequestException;

    Message findAllBoxMediaByUserId(Long userId) throws EntityNotFoundException;

    Message updateBoxMedia(Long id, String boxNumber, String nearbyLocation, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message<BoxMedia> deleteBoxMedia(Long id, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

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
            throws EntityNotSavedException, BadRequestException, EntityNotFoundException;

    Message findAllPackageByUserId(Long userId) throws EntityNotFoundException;

    Message updatePackage(PackageRequest packageRequest, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    Message<Package> deletePackage(Long packageId, User user)
            throws BadRequestException, EntityNotSavedException, EntityNotFoundException;

    // Customer CRUD
    Message createCustomer(CustomerRequest dto, User user) throws PermissionException, BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message findCustomerById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllCustomerByUserId(Long userId) throws EntityNotFoundException;

    Message findCustomerByFilter(CustomerFilterAndPaginationRequest customerFilterDTO, User user) throws EntityNotFoundException;

    Message<CustomerFilterAndPaginationResponse> getAllCustomersByUserCollections(PaginatedCustomersInUserCollectionRequest request, User user) throws EntityNotFoundException;
}
