package com.clickpay.service.company;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.company.Company;

import java.util.List;

public interface ICompanyService {
    Company findById(Long id) throws EntityNotFoundException, BadRequestException;

    Company save(Company company, boolean isUpdating) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException;

    List<Company> findAllCompanyByUserId(Long userId) throws EntityNotFoundException;
}
