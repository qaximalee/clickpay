package com.clickpay.service.user.user_feature;

import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.UserFeature;

public interface IUserFeatureService {
    UserFeature findByUserId(Long id, String moduleName) throws PermissionException;
}
