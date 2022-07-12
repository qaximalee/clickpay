package com.clickpay.service.user_profile.customer;

import com.clickpay.dto.user_profile.customer.CreateCustomerRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.User;
import com.clickpay.model.user_profile.Customer;
import org.springframework.transaction.annotation.Transactional;

public interface ICustomerService {

    @Transactional
    Customer createCustomer(CreateCustomerRequest dto, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, PermissionException;

    Customer save(Customer save) throws BadRequestException, EntityNotSavedException;
}
