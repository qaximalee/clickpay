package com.clickpay.service.user;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user.User;

public interface IUserService {
    User findByUsername(String username) throws EntityNotFoundException;

    User save(User user) throws BadRequestException, EntityNotSavedException;

    boolean existsByUsernameOREmail(String internetId, String email);
}
