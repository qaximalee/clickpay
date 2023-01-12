package com.clickpay.service.user_profile.packages;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.repository.user_profile.PackageRepository;
import com.clickpay.model.user_profile.Package;
import com.clickpay.service.user.IUserService;
import com.clickpay.service.validation.IValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PackageService implements IPackageService{

    private final IUserService userService;
    private final PackageRepository repo;
    private final IValidationService<Package> validationService;

    public PackageService(final IUserService userService,
                          final PackageRepository repo, IValidationService<Package> validationService) {
        this.userService = userService;
        this.repo = repo;
        this.validationService = validationService;
    }

    @Override
    public Package findById(Long id) throws EntityNotFoundException, BadRequestException {
        log.info("Finding package by id: "+id);
        if (id == null || id < 1) {
            log.error("Package id " + id + " is invalid.");
            throw new BadRequestException("Provided package id should be a valid and non null value.");
        }
        Optional<Package> data = repo.findByIdAndIsDeleted(id, false);
        if (!data.isPresent()) {
            log.error("No package found with id: "+id);
            throw new EntityNotFoundException("No package found with provided city id.");
        }
        return data.get();
    }

    @Override
    public Package save(Package packageData) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException {
        log.info("Creating package.");
        if (packageData == null) {
            log.error("Package should not be null.");
            throw new BadRequestException("Package should not be null.");
        }

        validationService.getRecords(
                Package.class,
                "packageName",
                "createdBy",
                packageData.getPackageName(),
                packageData.getCreatedBy(),
                "Package name: "+packageData.getPackageName()+" already exists."
        );

        try {
            packageData = repo.save(packageData);
            log.debug("Package with city id: "+packageData.getId()+ " created successfully.");
            return packageData;
        } catch (Exception e) {
            log.error("Package can not be saved.");
            throw new EntityNotSavedException("Package can not be saved.");
        }
    }

    @Override
    public List<Package> findAllPackageByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching all package for user id: "+userId);
        List<Package> packageDataList = repo.findAllByCreatedByAndIsDeleted(userId,false);
        if (packageDataList == null || packageDataList.isEmpty()) {
            log.error("No package data found.");
            throw new EntityNotFoundException("Package not found.");
        }
        return packageDataList;
    }
}
