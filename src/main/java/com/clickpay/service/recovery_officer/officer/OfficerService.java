package com.clickpay.service.recovery_officer.officer;

import com.clickpay.dto.recovery_officer.officer.*;
import com.clickpay.dto.recovery_officer.recovery_officer_collection.RecoveryOfficerCustomerDropdownDto;
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
import com.clickpay.utils.StringUtil;
import com.clickpay.utils.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OfficerService implements IOfficerService {

    private final OfficerRepository repo;
    private final IUserService userService;
    private final IUserTypeService userTypeService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OfficerService(OfficerRepository repo, IUserService userService, IUserTypeService userTypeService, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userService = userService;
        this.userTypeService = userTypeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public OfficerResponse createOfficer(OfficerRequest request, User user)
            throws EntityAlreadyExistException, BadRequestException, EntityNotFoundException, EntityNotSavedException {
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
        UserType userType = userTypeService.findByUserTypeName(UserType.OFFICER_TYPE);
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

    private Officer save(Officer officer) throws BadRequestException, EntityNotSavedException {
        if (officer == null) {
            log.error("Officer should not be null.");
            throw new BadRequestException("Officer should not be null.");
        }
        try {
            officer = repo.save(officer);
            log.debug("Officer with officer id: " + officer.getId() + " created successfully.");
            return officer;
        } catch (Exception e) {
            log.error("Officer can not be saved.");
            throw new EntityNotSavedException("Officer can not be saved.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public OfficerResponse findById(Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || id < 1) {
            log.error("Officer id " + id + " is invalid.");
            throw new BadRequestException("Provided officer id should be a valid and non null value.");
        }

        Optional<Officer> officer = repo.findById(id);
        if (officer == null || !officer.isPresent()) {
            log.error("No officer found with officer id: " + id);
            throw new EntityNotFoundException("No officer found with provided officer id.");
        }
        return OfficerResponse.fromOfficer(officer.get());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Officer> findAllOfficersByUserId(String status, Integer pageNo, Integer pageSize, Long userId) throws EntityNotFoundException, BadRequestException {

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Officer> officers = null;
        if(status == null)
            officers = repo.findAllByCreatedByAndStatus(userId,status,pageable);
        else
            officers = repo.findAllByCreatedBy(userId, pageable);


        if (officers == null || officers.isEmpty()) {
            log.error("No officer data found.");
            throw new EntityNotFoundException("Officer list not found.");
        }
        return officers;
    }

    @Transactional
    @Override
    public OfficerResponse updateOfficer(OfficerUpdateRequest request, User user)
            throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException {
        if (request.getId() == null || request.getId() < 1) {
            log.error("Officer id " + request.getId() + " is invalid.");
            throw new BadRequestException("Provided officer id should be a valid and non null value.");
        }

        Optional<Officer> officer = repo.findById(request.getId());
        if (officer == null || !officer.isPresent()) {
            log.error("No officer found with officer id: " + request.getId());
            throw new EntityNotFoundException("No officer found with provided officer id.");
        }
        if(officer.get().getEmail() != request.getEmail() ) {
            // Check if email already exists
            if (userService.existsByEmail(request.getEmail())) {
                log.error("User already exists with email.");
                throw new EntityAlreadyExistException("User already exists with email.");
            }
        }
        if (officer.get().getUserName() != request.getUsername()) {
            // Check if username already exists
            if (userService.existsByUsername(request.getUsername())) {
                log.error("User already exists with username.");
                throw new EntityAlreadyExistException("User already exists with username.");
            }
        }

        String firstAndLastName[] = StringUtil.extractFirstNameAndLastNameFromNameField(request.getName());

        User savingUser = officer.get().getUser();
        savingUser.setFirstName(firstAndLastName[0]);
        savingUser.setUsername(request.getUsername());
        savingUser.setLastName(firstAndLastName[1]);
        savingUser.setEmail(request.getEmail());
        savingUser.setPassword(passwordEncoder.encode(request.getPassword()));

        savingUser.setModifiedBy(user.getId());
        savingUser.setLastModifiedDate(new Date());

        userService.save(savingUser);

        Officer updatingOfficer = officer.get();
        updatingOfficer.setName(request.getName());
        updatingOfficer.setSalary(request.getSalary());
        updatingOfficer.setCnic(request.getCnic());
        updatingOfficer.setEmail(request.getEmail());
        updatingOfficer.setCellNo(request.getCellNo());
        updatingOfficer.setAddress(request.getAddress());
        updatingOfficer.setLeavingDate(request.getLeavingDate());
        updatingOfficer.setStatus(Status.of(request.getStatus()));
        updatingOfficer.setPassword(request.getPassword());
        updatingOfficer.setUserName(request.getUsername());

        updatingOfficer.setModifiedBy(user.getId());
        updatingOfficer.setLastModifiedDate(new Date());

        updatingOfficer = repo.save(updatingOfficer);

        return OfficerResponse.fromOfficer(updatingOfficer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Officer> getAllUsersWithRespectToTheAdminOrARecoveryOfficerId(User user) throws EntityNotFoundException {
        log.info("Fetching all customer data by logged in user or recovery officer id.");
        List<Officer> officers = new ArrayList<>();
        if (user.getUserType().getType().equalsIgnoreCase(Officer.OFFICER)) {
            Optional<Officer> officer = repo.findByUserIdAndActive(user.getId(), true);
            if (!officer.isPresent()) {
                log.error("Officer not found with id: "+user.getId());
                throw new EntityNotFoundException("Officer not found with id: "+user.getId());
            }
            officers.add(officer.get());
        }else{
            officers = repo.findAllByCreatedBy(user.getId());
            if (CollectionUtils.isEmpty(officers)) {
                log.error("Officer not found for the logged in user.");
                throw new EntityNotFoundException("Officer not found with id: "+user.getId());
            }
        }
        return officers;
    }
}
