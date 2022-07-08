package com.clickpay.service.area.locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.Locality;

import java.util.List;

public interface ILocalityService {
    Locality findById(Long id) throws BadRequestException, EntityNotFoundException;

    Locality save(Locality locality) throws BadRequestException, EntityNotSavedException;

    List<Locality> findAllLocalityByUserId(Long userId) throws EntityNotFoundException;
}
