package com.clickpay.service.area.sub_locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.SubLocality;

import java.util.List;

public interface ISubLocalityService {
    SubLocality findById(Long id) throws BadRequestException, EntityNotFoundException;

    SubLocality save(SubLocality subLocality) throws BadRequestException, EntityNotSavedException;

    List<SubLocality> findAllLocalityByUserId(Long userId) throws EntityNotFoundException;
}
