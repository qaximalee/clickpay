package com.clickpay.service.user_profile.customer;

import com.clickpay.dto.user_profile.customer.CustomerFilterDTO;
import com.clickpay.dto.user_profile.customer.CustomerRequest;
import com.clickpay.dto.user_profile.customer.CustomerResponse;
import com.clickpay.errors.general.*;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import com.clickpay.utils.Message;
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

    @Transactional(readOnly = true)
    List<CustomerResponse> findCustomerByFilter(CustomerFilterDTO customerFilterDTO, User user) throws EntityNotFoundException;
}
