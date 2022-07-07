package com.clickpay.service.user.user_type_feature;

import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.UserTypeFeature;
import com.clickpay.repository.user.UserTypeFeatureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserTypeFeatureService implements IUserTypeFeatureService{

    private final UserTypeFeatureRepository repo;

    @Autowired
    public UserTypeFeatureService(final UserTypeFeatureRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserTypeFeature findByUserTypeIdAndFeature_Value(Long userTypeId, String moduleName) throws PermissionException {
        UserTypeFeature userTypeFeature = repo.findByUserTypeIdAndFeature_Value(userTypeId, moduleName);
        if (userTypeFeature == null) {
            log.error("User type feature not found with provided user type id and moduleName.");
            throw new PermissionException("You doesn't have permission for this resource.");
        }
        return userTypeFeature;
    }
}
