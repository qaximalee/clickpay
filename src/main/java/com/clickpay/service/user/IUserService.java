package com.clickpay.service.user;

import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.user.User;

public interface IUserService {
    User findByUsername(String username) throws EntityNotFoundException;
}
