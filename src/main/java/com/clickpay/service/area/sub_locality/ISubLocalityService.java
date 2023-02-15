package com.clickpay.service.area.sub_locality;

import com.clickpay.dto.area.SubLocalityResponse;
import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityAlreadyExistException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.area.SubLocality;

import java.util.List;

public interface ISubLocalityService {
    SubLocality findById(Long id) throws BadRequestException, EntityNotFoundException;

    SubLocality save(SubLocality subLocality) throws BadRequestException, EntityNotSavedException, EntityAlreadyExistException;

    List<SubLocalityResponse> findAllSubLocalityByUserId(Long userId) throws EntityNotFoundException;

    List<SubLocality> findAllByIdInAndUserId(List<Long> subLocalitiesId, long id) throws EntityNotFoundException;

    List<SubLocality> findAllSubLocalitiesByUserId(Long userId) throws EntityNotFoundException;
}
