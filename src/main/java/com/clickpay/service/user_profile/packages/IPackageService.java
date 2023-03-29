package com.clickpay.service.user_profile.packages;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user_profile.Package;

import java.util.List;

public interface IPackageService {
    Package findById(Long id) throws EntityNotFoundException, BadRequestException;

    Package save(Package packageData, boolean isUpdating) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException;

    List<Package> findAllPackageByUserId(Long userId) throws EntityNotFoundException;

    List<Package> findAllPackageByCompanyIdAndUserId(Long companyId, Long userId) throws EntityNotFoundException;
}
