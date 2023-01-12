package com.clickpay.service.area.locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.Locality;
import com.clickpay.repository.area.LocalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LocalityService implements ILocalityService{

    private final LocalityRepository repo;

    @Autowired
    public LocalityService(final LocalityRepository repo) {
        this.repo = repo;
    }

    @Override
    public Locality findById(Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || id < 1) {
            log.error("Locality id "+id+ " is invalid.");
            throw new BadRequestException("Provided locality id should be a valid and non null value.");
        }

        Optional<Locality> locality = repo.findByIdAndIsDeleted(id,false);
        if (!locality.isPresent()) {
            log.error("No locality found with locality id: "+id);
            throw new EntityNotFoundException("No locality found with provided locality id.");
        }
        return locality.get();
    }

    @Override
    public Locality save(Locality locality) throws BadRequestException, EntityNotSavedException {
        if (locality == null) {
            log.error("Locality should not be null.");
            throw new BadRequestException("Locality should not be null.");
        }

        try {
            locality = repo.save(locality);
            log.debug("Locality with locality id: "+locality.getId()+ " created successfully.");
            return locality;
        } catch (Exception e) {
            log.error("Locality can not be saved.");
            throw new EntityNotSavedException("Locality can not be saved.");
        }
    }

    @Override
    public List<Locality> findAllLocalityByUserId(Long userId) throws EntityNotFoundException {
        List<Locality> localityList = repo.findAllByCreatedByAndIsDeleted(userId,false);
        if (localityList == null || localityList.isEmpty()) {
            log.debug("No locality data found.");
            throw new EntityNotFoundException("Locality list not found.");
        }
        return localityList;
    }
}
