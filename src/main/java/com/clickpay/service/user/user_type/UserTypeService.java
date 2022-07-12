package com.clickpay.service.user.user_type;

import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.PermissionException;
import com.clickpay.model.user.UserType;
import com.clickpay.repository.user.UserTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserTypeService implements IUserTypeService{

    private final UserTypeRepository repo;

    @Autowired
    public UserTypeService(final UserTypeRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserType findByUserTypeName(String userTypeName) throws EntityNotFoundException {
        UserType userType = repo.findByTypeIgnoreCase(userTypeName);
        if (userType == null) {
            log.error("User type with type name: "+userTypeName+" is not found.");
            throw new EntityNotFoundException("User type not found with provided type name.");
        }

        return userType;
    }
}
