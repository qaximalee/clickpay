package com.clickpay.service.area;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;
import com.clickpay.model.area.Locality;
import com.clickpay.model.area.SubLocality;
import com.clickpay.model.user.User;
import com.clickpay.utils.Message;

public interface IAreaService {

    /**
     * For CRUD operations of city
     * */
    Message createCity(String name, Long countryId, User user) throws EntityNotFoundException, EntityNotSavedException, BadRequestException, EntityAlreadyExistException;
    
    Message findCityById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllCityByUserId(Long userId) throws BadRequestException, EntityNotFoundException;

    Message updateCity(Long id, String name, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message<City> deleteCity(Long id, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    /**
     * For CRUD operations of locality
     * */

    Message findLocalityById(Long id) throws BadRequestException, EntityNotFoundException;

    Message createLocality(String name, Long cityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message updateLocality(Long id, String name, Long cityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message findAllLocalityByUserId(Long userId) throws EntityNotFoundException;

    Message<Locality> deleteLocality(Long id, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    /**
     * For CRUD operations of sub locality
     * */

    Message createSubLocality(String name, Long localityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message findSubLocalityById(Long id) throws BadRequestException, EntityNotFoundException;

    Message findAllSubLocalityByUserId(Long userId) throws EntityNotFoundException;

    Message updateSubLocality(Long id, String name, Long localityId, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;

    Message<SubLocality> deleteSubLocality(Long id, User user) throws BadRequestException, EntityNotFoundException, EntityNotSavedException, EntityAlreadyExistException;
}
