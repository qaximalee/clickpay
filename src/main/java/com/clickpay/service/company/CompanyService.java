package com.clickpay.service.company;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.company.Company;
import com.clickpay.repository.company.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyService implements ICompanyService {

    private final CompanyRepository repo;

    public CompanyService(final CompanyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Company findById(Long id) throws EntityNotFoundException, BadRequestException {
        log.info("Finding company by id: "+id);
        if (id == null || id < 1) {
            log.error("Company id " + id + " is invalid.");
            throw new BadRequestException("Provided company id should be a valid and non null value.");
        }
        Optional<Company> data = repo.findByIdAndActiveAndIsDeleted(id, true, false);
        if (!data.isPresent()) {
            log.error("No company found with id: "+id);
            throw new EntityNotFoundException("No company found with provided company id.");
        }
        return data.get();
    }

    @Override
    public Company save(Company company) throws EntityNotSavedException, BadRequestException {
        log.info("Creating company.");
        if (company == null) {
            log.error("Company should not be null.");
            throw new BadRequestException("Company should not be null.");
        }

        try {
            company = repo.save(company);
            log.debug("Company with city id: "+company.getId()+ " created successfully.");
            return company;
        } catch (Exception e) {
            log.error("Company can not be saved.");
            throw new EntityNotSavedException("Company can not be saved.");
        }
    }

    @Override
    public List<Company> findAllCompanyByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching all company for user id: "+userId);
        List<Company> companyList = repo.findAllByCreatedByAndActiveAndIsDeleted(userId, true, false);
        if (companyList == null || companyList.isEmpty()) {
            log.error("No company data found.");
            throw new EntityNotFoundException("Company not found.");
        }
        return companyList;
    }
}
