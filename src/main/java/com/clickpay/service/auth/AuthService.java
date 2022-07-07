package com.clickpay.service.auth;

import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.User;
import com.clickpay.service.feature.IFeatureService;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.user.user_feature.IUserFeatureService;
import com.clickpay.service.user.user_type_feature.IUserTypeFeatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class AuthService implements IAuthService {

    private static final long SUPERADMIN_ID = 1L;

    private final IUserService userService;
    private final IFeatureService featureService;
    private final IUserFeatureService userFeatureService;
    private final IUserTypeFeatureService userTypeFeatureService;

    @Autowired
    public AuthService(final IUserService userService,
                       final IFeatureService featureService,
                       final IUserFeatureService userFeatureService,
                       final IUserTypeFeatureService userTypeFeatureService) {
        this.userService = userService;
        this.featureService = featureService;
        this.userFeatureService = userFeatureService;
        this.userTypeFeatureService = userTypeFeatureService;
    }

    @Override
    public User hasPermission(String moduleName, Principal principal) throws PermissionException {
        log.info("Checking permission...");
        User user = null;
        try {
            user = userService.findByUsername(principal.getName());
            if (user.getId() == SUPERADMIN_ID) {
                log.info("User is a super admin.");
                return user;
            }
            if (user.getIsCustomPermission()) {
                log.info("This user has all permissions of user type: " + user.getUserType().getType() + ".");
                // TODO check moduleName in the user_feature table
                userFeatureService.findByUserId(user.getId(), moduleName);
                return user;
            } else {
                log.info("This user has custom permissions.");
                // TODO check moduleName in the user_type_feature table
                userTypeFeatureService.findByUserTypeIdAndFeature_Value(user.getUserType().getId(), moduleName);
                return user;
            }
        } catch (EntityNotFoundException e) {
            log.error("Access token is not correct. User not found with the username: "+principal.getName());
            throw new PermissionException("Access token is not correct.");
        }
    }
}
