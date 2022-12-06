package com.clickpay.service.user_profile.customer;

import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationRequest;
import com.clickpay.dto.user_profile.customer.CustomerFilterAndPaginationResponse;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICustomerService {

    @Transactional
    Customer createCustomer(CustomerRequest dto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException, EntityAlreadyExistException;

    @Transactional
    Customer save(Customer save) throws BadRequestException, EntityNotSavedException;

    @Transactional(readOnly = true)
    Customer findById(Long id) throws BadRequestException, EntityNotFoundException;

    @Transactional(readOnly = true)
    List<CustomerResponse> findAllCustomerById(Long userId) throws EntityNotFoundException;

    // for unpaid collections
    @Transactional(readOnly = true)
    Customer findAllCustomerByUserId(Long userId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    CustomerFilterAndPaginationResponse findCustomerByFilter(CustomerFilterAndPaginationRequest customerFilterDTO, User user) throws EntityNotFoundException;
}
