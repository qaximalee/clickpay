package com.clickpay.service.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

import java.security.Principal;

public interface IAreaService {

    /**
     * For CRUD operations of city
     * */
    Message createCity(String name, Long countryId, User user) throws EntityNotFoundException, EntityNotSavedException, BadRequestException;
    
    Message findCityById(Long id) throws BadRequestException, EntityNotFoundException;

    /**
     * For CRUD operations of locality
     * */
    Message findLocalityById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllCity() throws BadRequestException, EntityNotFoundException;

    Message updateCity(Long id, String name, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message createLocality(String name, Long cityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;


    Message updateLocality(Long id, String name, Long cityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message findAllLocality() throws EntityNotFoundException;

    /**
     * For CRUD operations of sub locality
     * */
    Message createSubLocality(String name, Long localityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;

    Message findSubLocalityById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllSubLocality() throws EntityNotFoundException;

    Message updateSubLocality(Long id, String name, Long localityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException;
}
