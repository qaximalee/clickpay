package com.clickpay.service.validation;

import com.clickpay.errors.general.EntityAlreadyExistException;

public interface IValidationService<T> {
    void getRecords(Class clazz, String fieldName, String createdBy, String value, Long userId, String errorMessage, boolean forDeletion) throws EntityAlreadyExistException;
}
