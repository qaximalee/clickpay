package com.clickpay.utils.enums;

import com.clickpay.errors.general.BadRequestException;

import java.util.Locale;

public enum Status {
    ACTIVE, INACTIVE;

    public static Status of(String status) throws BadRequestException {
        Status statusData = null;
        try {
            statusData = Status.valueOf(status.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Status with name: "+status+" is not valid.");
        }
        return statusData;
    }
}
