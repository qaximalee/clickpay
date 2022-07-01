package com.clickpay.service.area.country;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.model.area.Country;

public interface ICountryService {
    Country findById(Long id) throws BadRequestException, EntityNotFoundException;
}
