package com.clickpay.service.area.sub_locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.SubLocality;
import com.clickpay.repository.area.SubLocalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
