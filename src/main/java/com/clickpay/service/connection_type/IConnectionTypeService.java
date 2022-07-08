package com.clickpay.service.connection_type;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.model.connection_type.ConnectionType;

import java.util.List;

public interface IConnectionTypeService {
    ConnectionType findById(Long id) throws EntityNotFoundException, BadRequestException;

    ConnectionType save(ConnectionType connectionType) throws EntityNotSavedException, BadRequestException;

    List<ConnectionType> findAllConnectionTypeByUserId(Long userId) throws EntityNotFoundException;
}
