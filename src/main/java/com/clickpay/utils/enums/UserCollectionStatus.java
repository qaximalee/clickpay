package com.clickpay.utils.enums;

import com.clickpay.errors.general.BadRequestException;

import java.util.Locale;

public enum UserCollectionStatus {
    PAID, UNPAID;

    public static UserCollectionStatus of(String userCollectionStatus) throws BadRequestException {
        UserCollectionStatus userCollectionStatusData = null;
        try {
            userCollectionStatusData = UserCollectionStatus.valueOf(userCollectionStatus.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Status with name: "+userCollectionStatus+" is not valid.");
        }
        return userCollectionStatusData;
    }
}
