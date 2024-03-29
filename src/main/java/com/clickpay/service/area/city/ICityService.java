package com.clickpay.service.area.city;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.City;

import java.util.List;

public interface ICityService {
    City findById(Long id) throws BadRequestException, EntityNotFoundException;

    City save(City city, boolean isUpdating) throws EntityNotSavedException, BadRequestException, EntityAlreadyExistException;

    List<City> findAllCityByUserId(Long userId) throws EntityNotFoundException;
}
