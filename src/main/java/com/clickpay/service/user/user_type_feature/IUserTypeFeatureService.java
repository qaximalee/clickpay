package com.clickpay.service.user.user_type_feature;

import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.UserTypeFeature;

public interface IUserTypeFeatureService {
    UserTypeFeature findByUserTypeIdAndFeature_Value(Long userTypeId, String moduleName) throws PermissionException;
}
