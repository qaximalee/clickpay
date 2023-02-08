package com.clickpay.service.area.sub_locality;

import com.clickpay.dto.area.SubLocalityResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.Locality;
import com.clickpay.model.area.SubLocality;
import com.clickpay.repository.area.SubLocalityRepository;
import com.clickpay.service.validation.IValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SubLocalityService implements ISubLocalityService {

    private final SubLocalityRepository repo;
    private final IValidationService<SubLocality> validationService;

    @Autowired
    public SubLocalityService(final SubLocalityRepository repo, IValidationService<SubLocality> validationService) {
        this.repo = repo;
        this.validationService = validationService;
    }

    @Override
    public SubLocality findById(Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || id < 1) {
            log.error("Sub locality id "+id+ " is invalid.");
            throw new BadRequestException("Provided sub locality id should be a valid and non null value.");
        }

        Optional<SubLocality> subLocality = repo.findByIdAndIsDeleted(id, false);

        return subLocality.orElseThrow(() -> {
            log.error("No sub locality found with city id: "+id);
            return new EntityNotFoundException("No sub locality found with provided city id.");
        });
    }

    @Override
    public SubLocality save(SubLocality subLocality) throws BadRequestException, EntityNotSavedException, EntityAlreadyExistException {
        if (subLocality == null) {
            log.error("Sub locality should not be null.");
            throw new BadRequestException("Sub locality should not be null.");
        }

        validationService.getRecords(
                SubLocality.class,
                "name",
                "createdBy",
                subLocality.getName(),
                subLocality.getCreatedBy(),
                "Sub Locality name: "+subLocality.getName()+" already exists."
        );

        try {
            subLocality = repo.save(subLocality);
            log.debug("Sub locality with locality id: "+subLocality.getId()+ " created successfully.");
            return subLocality;
        } catch (Exception e) {
            log.error("Sub locality can not be saved.");
            throw new EntityNotSavedException("Sub locality can not be saved.");
        }
    }

    @Override
    public List<SubLocalityResponse> findAllSubLocalityByUserId(Long userId) throws EntityNotFoundException {
        List<SubLocality> subLocalities = repo.findAllByCreatedByAndIsDeleted(userId, false);
        if (subLocalities == null || subLocalities.isEmpty()) {
            log.debug("No sub locality data found.");
            throw new EntityNotFoundException("Sub locality list not found.");
        }
        return SubLocalityResponse.mapFromSubLocality(subLocalities);
    }
}
