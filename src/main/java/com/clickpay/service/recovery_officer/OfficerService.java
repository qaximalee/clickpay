package com.clickpay.service.recovery_officer;

import com.clickpay.dto.recovery_officer.officer.OfficerResponse;
import com.clickpay.dto.recovery_officer.officer.OfficerRequest;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.recovery_officer.Officer;
import com.clickpay.model.user.User;
import com.clickpay.model.user.UserType;
import com.clickpay.repository.recovery_officer.OfficerRepository;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.user.user_type.IUserTypeService;
import com.clickpay.utils.Constant;
import com.clickpay.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class OfficerService implements IOfficerService{

    private final OfficerRepository repo;
    private final IUserService userService;
    private final IUserTypeService userTypeService;
    private final PasswordEncoder passwordEncoder;

    public OfficerService(OfficerRepository repo, IUserService userService, IUserTypeService userTypeService, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userService = userService;
        this.userTypeService = userTypeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OfficerResponse createOfficer(OfficerRequest request, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Creating officer with provided request body.");

        // Check if username or email already exists
        if (userService.existsByUsernameOREmail(request.getUserName(), request.getEmail())) {
            log.error("User already exists with username or email.");
            throw new EntityAlreadyExistException("User already exists with username or email.");
        }

        User savedUser = createUser(request, user);

        log.info("Populate officer data.");

        Officer officer = new Officer();
        officer.setName(request.getName());
        officer.setAddress(request.getAddress());
        officer.setEmail(request.getEmail());
        officer.setPassword(request.getPassword());
        officer.setCellNo(request.getCellNo());
        officer.setJoiningDate(request.getJoiningDate());
        officer.setCnic(request.getCnic());
        officer.setSalary(request.getSalary());
        officer.setUserName(request.getUserName());
        officer.setUser(savedUser);
        officer.setCreatedBy(user.getId());
        officer.setCreationDate(new Date());

        return OfficerResponse.fromOfficer(save(officer));
    }

    private User createUser(OfficerRequest request, User user)
            throws EntityNotFoundException, BadRequestException, EntityNotSavedException {
        log.info("Creating user with officer data.");
        UserType userType = userTypeService.findByUserTypeName(Constant.OFFICER);
        String firstAndLastName[] = StringUtil.extractFirstNameAndLastNameFromNameField(request.getName());

        User createUser = new User();
        createUser.setFirstName(firstAndLastName[0]);
        createUser.setLastName(firstAndLastName[1]);
        createUser.setEmail(request.getEmail());
        createUser.setUserType(userType);
        createUser.setIsCustomPermission(false);
        createUser.setUsername(request.getUserName());
        createUser.setPassword(passwordEncoder.encode(request.getPassword()));
        createUser.setCreatedBy(user.getId());
        createUser.setCreationDate(new Date());
        createUser.setVerified(true);

        return userService.save(createUser);
    }

    @Transactional
    @Override
    public Officer save(Officer officer) throws BadRequestException, EntityNotSavedException {
        log.info("Creating officer in database.");

        if (officer == null) {
            log.error("Officer should not be null.");
            throw new BadRequestException("Officer should not be null.");
        }
        try {
            officer = repo.save(officer);
            log.debug("Officer with officer id: "+officer.getId()+ " created successfully.");
            return officer;
        } catch (Exception e) {
            log.error("Officer can not be saved.");
            throw new EntityNotSavedException("Officer can not be saved.");
        }
    }
}
