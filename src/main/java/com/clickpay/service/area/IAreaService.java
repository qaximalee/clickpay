package com.clickpay.service.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.utils.Message;

import java.security.Principal;

public interface IAreaService {

    /**
     * For CRUD operations of city
     * */
    Message createCity(String name, Long countryId, Principal principal) throws EntityNotFoundException, EntityNotSavedException, BadRequestException;
    
    Message findCityById(Long id, Principal principal) throws BadRequestException, EntityNotFoundException;

    /**
     * For CRUD operations of locality
     * */
    Message findLocalityById(Long id);

    Message createLocality(String name, String cityId, Principal principal);


    /**
     * For CRUD operations of sub locality
     * */
    Message createSubLocality(String name, String localityId, Principal principal);

    Message findSubLocalityById(Long id);
}
