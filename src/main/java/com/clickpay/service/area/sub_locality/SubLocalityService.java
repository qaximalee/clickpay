package com.clickpay.service.area.sub_locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.model.area.SubLocality;
import com.clickpay.repository.area.SubLocalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SubLocalityService implements ISubLocalityService {

    private final SubLocalityRepository repo;

    @Autowired
    public SubLocalityService(final SubLocalityRepository repo) {
        this.repo = repo;
    }

    @Override
    public SubLocality findById(Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || id < 1) {
            log.error("Sub locality id "+id+ " is invalid.");
            throw new BadRequestException("Provided sub locality id should be a valid and non null value.");
        }

        Optional<SubLocality> subLocality = repo.findById(id);
        if (subLocality == null) {
            log.error("No sub locality found with city id: "+id);
            throw new EntityNotFoundException("No sub locality found with provided city id.");
        }
        return subLocality.get();
    }

    @Override
    public SubLocality save(SubLocality subLocality) throws BadRequestException, EntityNotSavedException {
        if (subLocality == null) {
            log.error("Sub locality should not be null.");
            throw new BadRequestException("Sub locality should not be null.");
        }

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
    public List<SubLocality> findAllLocalityByUserId(Long userId) throws EntityNotFoundException {
        List<SubLocality> subLocalities = repo.findAllByCreatedBy(userId);
        if (subLocalities == null || subLocalities.isEmpty()) {
            log.debug("No sub locality data found.");
            throw new EntityNotFoundException("Sub locality list not found.");
        }
        return subLocalities;
    }
}
