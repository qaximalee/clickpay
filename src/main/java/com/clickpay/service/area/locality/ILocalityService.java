package com.clickpay.service.area.locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.Locality;

public interface ILocalityService {
    Locality findById(Long id) throws BadRequestException, EntityNotFoundException;
}
