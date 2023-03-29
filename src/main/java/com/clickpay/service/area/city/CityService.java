package com.clickpay.service.area.city;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.model.company.Company;
import com.clickpay.repository.area.CityRepository;
import com.clickpay.service.validation.IValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CityService implements ICityService{

    private final CityRepository repo;
    private final IValidationService<City> validationService;

    @Autowired
    public CityService(final CityRepository repo, IValidationService<City> validationService) {
        this.repo = repo;
        this.validationService = validationService;
    }

    @Transactional(readOnly = true)
    @Override
    public City findById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Finding city by id: "+id);
        if (id == null || id < 1) {
            log.error("City id "+id+ " is invalid.");
            throw new BadRequestException("Provided city id should be a valid and non null value.");
        }

        Optional<City> city = repo.findByIdAndIsDeleted(id,false);

        return city.orElseThrow(() -> {
            log.error("No city found with city id: "+id);
            return new EntityNotFoundException("No city found with provided city id.");
        });
    }


    /**
     * FOR UPDATE: Name of city can't be an existing city name of a createdBy user and is not deleted yet
     * FOR CREATE: Check with the city name already city is not present and is not deleted for created by user
     * FOR DELETE: user deleting the city should be the creator of the object
     * */
    @Transactional
    @Override
    public City save(City city, boolean isUpdating) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException {
        log.info("Saving city.");
        if (city == null) {
            log.error("City should not be null.");
            throw new BadRequestException("City should not be null.");
        }
        // CREATE: ID will not be present, Name not be already exists, Creation user should be same.
        // UPDATE: ID will be present, NAME not be already exists, Creation user should be same. 
        // DELETE: ID will be present, DELETION User should be same as creation user. 

        // For creation
        if (city.getId() == null || (city.getId() != null && isUpdating)) {
            validationService.getRecords(
                    City.class,
                    "name",
                    "createdBy",
                    city.getName(),
                    city.getCreatedBy(),
                    "City name: "+city.getName()+" already exists.", false
            );
        } else { // for deletion so forUpdation present false
            // Check if the user created this city is deleting this object
            validationService.getRecords(
                    City.class,
                    "id",
                    "createdBy",
                    ""+city.getId(),
                    city.getCreatedBy(),
                    "City name: "+city.getName()+" not found for this user.", true
            );
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

    @Transactional(readOnly = true)
    @Override
    public List<City> findAllCityByUserId(Long userId) throws EntityNotFoundException {
        log.info("Fetching city list by user id: "+userId);
        List<City> cityList = repo.findAllByCreatedByAndIsDeleted(userId,false);
        if (cityList == null || cityList.isEmpty()) {
            log.error("No city data found.");
            throw new EntityNotFoundException("City list not found.");
        }
        return cityList;
    }
}
