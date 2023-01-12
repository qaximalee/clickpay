package com.clickpay.service.area.country;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.Country;
import com.clickpay.repository.area.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CountryService implements ICountryService{

    private final CountryRepository repo;

    @Autowired
    public CountryService(final CountryRepository repo) {
        this.repo = repo;
    }

    @Override
    public Country findById(Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || id < 1) {
            log.error("Country id "+id+ " is invalid.");
            throw new BadRequestException("Provided country id should be a valid and non null value.");
        }

        Optional<Country> country = repo.findById(id);
        if (country == null) {

        }
        return country.orElseThrow(() -> {
            log.error("No country found with city id: "+id);
            return new EntityNotFoundException("No country found with provided city id.");
        });
    }
}
