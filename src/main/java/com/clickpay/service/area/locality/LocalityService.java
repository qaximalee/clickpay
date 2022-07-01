package com.clickpay.service.area.locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.Locality;
import com.clickpay.repository.area.LocalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Optional<Locality> locality = repo.findById(id);
        if (locality == null) {
            log.error("No locality found with city id: "+id);
            throw new EntityNotFoundException("No locality found with provided city id.");
        }
        return locality.get();
    }
}
