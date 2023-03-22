package com.clickpay.service.user_profile.customer;

import com.clickpay.dto.user_profile.customer.*;
import com.clickpay.dto.recovery_officer.recovery_officer_collection.RecoveryOfficerCustomerDropdownDto;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationResponse;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICustomerService {

    @Transactional
    Customer createCustomer(CustomerRequest dto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException, EntityAlreadyExistException;

    @Transactional
    Customer updateCustomerStatus(Long customerId, boolean status, User modifiedByUser) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    @Transactional
    Customer save(Customer save) throws BadRequestException, EntityNotSavedException;

    @Transactional(readOnly = true)
    Customer findById(Long id) throws BadRequestException, EntityNotFoundException;

    @Transactional(readOnly = true)
    List<Customer> findAllCustomerById(Long userId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    List<Customer> findAllCustomerByIdAndConnectionTypeId(Long userId, Long connectionTypeId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    List<Customer> findAllCustomerByIdAndConnectionTypeIdAndSubLocalityId(Long userId, Long connectionTypeId, Long subLocalityId) throws EntityNotFoundException;

    // for unpaid collections
    @Transactional(readOnly = true)
    Customer findCustomerByUserId(Long userId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    CustomerFilterAndPaginationResponse findCustomerByFilter(CustomerFilterAndPaginationRequest customerFilterDTO, User user) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    List<CustomerResponse> getCustomersByFilter(CustomersInUserCollectionRequest request, User user) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    List<RecoveryOfficerCustomerDropdownDto> getAllUsersWithRespectToTheRecoveryOfficer(User user) throws EntityNotFoundException;
}
