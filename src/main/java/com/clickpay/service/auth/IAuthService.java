package com.clickpay.service.auth;

import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.User;

import java.security.Principal;

public interface IAuthService {
    User hasPermission(String moduleName, Principal principal) throws PermissionException;
}
