package com.clickpay.service.user.user_type;

import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.UserType;

public interface IUserTypeService {
    UserType findByUserTypeName(String userTypeName) throws EntityNotFoundException;
}
