package com.clickpay.service.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.model.user.User;
import com.clickpay.repository.area.CityRepository;
import com.clickpay.repository.area.CountryRepository;
import com.clickpay.repository.area.LocalityRepository;
import com.clickpay.repository.area.SubLocalityRepository;
import com.clickpay.service.area.city.CityService;
import com.clickpay.service.area.city.ICityService;
import com.clickpay.service.area.country.ICountryService;
import com.clickpay.service.area.locality.ILocalityService;
import com.clickpay.service.area.sub_locality.ISubLocalityService;
import com.clickpay.service.user.IUserService;
import com.clickpay.utils.Constant;
import com.clickpay.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    @Override
    public Message createCity(String name, Long countryId, Principal principal) throws EntityNotFoundException, EntityNotSavedException, BadRequestException {
        log.info("City creation is started.");

        // Fetched User form principal
        User user = userService.findByUsername(principal.getName());

        City city = new City();
        city.setName(name);
        city.setCountry(countryService.findById(countryId));
        city.setUser(user);
        city.setCreationDate(new Date());
        city.setCreatedBy(user.getId());

        city = cityService.save(city);

        log.debug("City: " + name + " is successfully created for user id: " + user.getId());
        return new Message<City>()
                .setStatus(HttpStatus.CREATED.value())
                .setCode(HttpStatus.CREATED.toString())
                .setMessage("City: " + name + " is successfully created.")
                .setData(city);
    }

    @Override
    public Message findCityById(Long id, Principal principal) throws BadRequestException, EntityNotFoundException {
        log.debug("City fetching by id: " + id);
        return new Message()
                .setData(cityService.findById(id))
                .setStatus(HttpStatus.OK.value())
                .setCode(HttpStatus.OK.toString())
                .setMessage("City"+ Constant.FETCHED_MESSAGE_SUCCESS);
    }

    @Override
    public Message findLocalityById(Long id) {
        return null;
    }

    @Override
    public Message createLocality(String name, String cityId, Principal principal) {
        return null;
    }

    @Override
    public Message createSubLocality(String name, String localityId, Principal principal) {
        return null;
    }

    @Override
    public Message findSubLocalityById(Long id) {
        return null;
    }
}
