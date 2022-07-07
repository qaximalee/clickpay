package com.clickpay.service.user.user_feature;

import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.UserFeature;
import com.clickpay.repository.user.UserFeatureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserFeatureService implements IUserFeatureService{

    private final UserFeatureRepository repo;

    @Autowired
    public UserFeatureService(final UserFeatureRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserFeature findByUserId(Long id, String moduleName) throws PermissionException {
        UserFeature userFeature = repo.findByUserIdAndFeature_Value(id, moduleName);
        if (userFeature == null) {
            log.error("User feature not found with provided user id and moduleName.");
            throw new PermissionException("You doesn't have permission for this resource.");
        }

        return userFeature;
    }
}
