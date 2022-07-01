package com.clickpay.service.area.sub_locality;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.SubLocality;

public interface ISubLocalityService {
    SubLocality findById(Long id) throws BadRequestException, EntityNotFoundException;
}
