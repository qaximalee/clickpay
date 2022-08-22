package com.clickpay.service.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.model.area.Locality;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.user.User;
import com.clickpay.service.area.city.ICityService;
import com.clickpay.service.area.country.ICountryService;
import com.clickpay.service.area.locality.ILocalityService;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.user.IUserService;
import com.clickpay.utils.ResponseMessage;
import com.clickpay.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AreaService implements IAreaService{

    private final ICountryService countryService;
    private final ICityService cityService;
    private final ILocalityService localityService;
    private final ISubLocalityService subLocalityService;
    private final IUserService userService;

    @Autowired
    public AreaService(final ICountryService countryService,
                       final ICityService cityService,
                       final ILocalityService localityService,
                       final ISubLocalityService subLocalityService,
                       final IUserService userService) {
        this.countryService = countryService;
        this.cityService = cityService;
        this.localityService = localityService;
        this.subLocalityService = subLocalityService;
        this.userService = userService;
    }

    /**
     * CRUD operations for city
     *
     * */
    @Override
    public Message createCity(String name, Long countryId, User user) throws EntityNotFoundException, EntityNotSavedException, BadRequestException {
        log.info("City creation is started.");

        City city = new City();
        city.setName(name);
        city.setCountry(countryService.findById(countryId));
        city.setCreationDate(new Date());
        city.setCreatedBy(user.getId());

        city = cityService.save(city);

        log.debug("City: " + name + " is successfully created for user id: " + user.getId());
        return new Message<City>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("City: " + name + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(city);
    }

    @Override
    public Message findCityById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("City fetching by id: " + id);
        return new Message()
                .setData(cityService.findById(id))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("City "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findAllCityByUserId(Long userId) throws EntityNotFoundException {
        log.info("City list is fetching.");
        return new Message()
                .setData(cityService.findAllCityByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("City list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateCity(Long id, String name, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("City updating with name: " + name);

        City city = cityService.findById(id);
        city.setModifiedBy(user.getId());
        city.setLastModifiedDate(new Date());
        city.setName(name);

        city = cityService.save(city);

        log.debug("City: " + name + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(city)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("City "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }


    /**
     * CRUD operations for locality
     *
     * */
    @Override
    public Message createLocality(String name, Long cityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Locality creation is started.");

        City city = cityService.findById(cityId);

        Locality locality = new Locality();
        locality.setName(name);
        locality.setCity(city);
        locality.setCreationDate(new Date());
        locality.setCreatedBy(user.getId());

        locality = localityService.save(locality);

        log.debug("Locality: " + name + " is successfully created for user id: " + user.getId());
        return new Message<Locality>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Locality: " + name + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(locality);
    }

    @Override
    public Message findLocalityById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Locality fetching by id: " + id);
        return new Message()
                .setData(localityService.findById(id))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Locality "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findAllLocalityByUserId(Long userId) throws EntityNotFoundException {
        log.info("Locality list is fetching.");
        return new Message()
                .setData(localityService.findAllLocalityByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Locality list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateLocality(Long id, String name, Long cityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Locality updating with name: " + name);

        Locality locality = localityService.findById(id);
        locality.setCity(cityService.findById(cityId));
        locality.setName(name);
        locality.setModifiedBy(user.getId());
        locality.setLastModifiedDate(new Date());

        locality = localityService.save(locality);

        log.debug("Locality: " + name + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(locality)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Locality "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }


    /**
     * CRUD operations for sub locality
     *
     * */
    @Override
    public Message createSubLocality(String name, Long localityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Locality creation is started.");

        Locality locality = localityService.findById(localityId);

        SubLocality subLocality = new SubLocality();
        subLocality.setName(name);
        subLocality.setLocality(locality);
        subLocality.setCreationDate(new Date());
        subLocality.setCreatedBy(user.getId());

        subLocality = subLocalityService.save(subLocality);

        log.debug("Sub locality: " + name + " is successfully created for user id: " + user.getId());
        return new Message<SubLocality>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("Sub locality: " + name + ResponseMessage.CREATED_MESSAGE_SUCCESS)
                .setData(subLocality);
    }

    @Override
    public Message findSubLocalityById(Long id) throws BadRequestException, EntityNotFoundException {
        log.info("Sub locality fetching by id: " + id);
        return new Message()
                .setData(subLocalityService.findById(id))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Sub locality "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findAllSubLocalityByUserId(Long userId) throws EntityNotFoundException {
        log.info("Sub locality list is fetching.");
        return new Message()
                .setData(subLocalityService.findAllLocalityByUserId(userId))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Sub locality list "+ ResponseMessage.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message updateSubLocality(Long id, String name, Long localityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException {
        log.info("Sub locality updating with name: " + name);

        SubLocality subLocality = subLocalityService.findById(id);
        subLocality.setLocality(localityService.findById(localityId));
        subLocality.setName(name);
        subLocality.setModifiedBy(user.getId());
        subLocality.setLastModifiedDate(new Date());

        subLocality = subLocalityService.save(subLocality);

        log.debug("Sub locality: " + name + " is successfully updated for user id: "+user.getId());
        return new Message()
                .setData(subLocality)
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("Sub locality "+ ResponseMessage.UPDATED_MESSAGE_SUCCESS);
    }
}
