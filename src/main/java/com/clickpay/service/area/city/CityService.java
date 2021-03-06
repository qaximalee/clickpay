package com.clickpay.service.area.city;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.repository.area.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CityService implements ICityService{

    private final CityRepository repo;

    @Autowired
    public CityService(final CityRepository repo) {
        this.repo = repo;
    }

    @Override
    public City findById(Long id) throws BadRequestException, EntityNotFoundException {
        if (id == null || id < 1) {
            log.error("City id "+id+ " is invalid.");
            throw new BadRequestException("Provided city id should be a valid and non null value.");
        }

        Optional<City> city = repo.findById(id);
        if (city == null) {
            log.error("No city found with city id: "+id);
            throw new EntityNotFoundException("No city found with provided city id.");
        }
        return city.get();
    }

    @Override
    public City save(City city) throws EntityNotSavedException, BadRequestException {
        if (city == null) {
            log.error("City should not be null.");
            throw new BadRequestException("City should not be null.");
        }

        try {
            city = repo.save(city);
            log.debug("City with city id: "+city.getId()+ " created successfully.");
            return city;
        } catch (Exception e) {
            log.error("City can not be saved.");
            throw new EntityNotSavedException("City can not be saved.");
        }
    }

    @Override
    public List<City> findAllCityByUserId(Long userId) throws EntityNotFoundException {
        List<City> cityList = repo.findAllByCreatedBy(userId);
        if (cityList == null || cityList.isEmpty()) {
            log.error("No city data found.");
            throw new EntityNotFoundException("City list not found.");
        }
        return cityList;
    }
}
