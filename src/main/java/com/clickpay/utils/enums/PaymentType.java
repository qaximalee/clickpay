package com.clickpay.utils.enums;

import com.clickpay.errors.general.BadRequestException;

import java.util.Locale;

public enum PaymentType {
    MONTHLY, INSTALLMENT, OTHER;

    public static PaymentType of(String paymentType) throws BadRequestException {
        PaymentType paymentData = null;
        try {
            paymentData = PaymentType.valueOf(paymentType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("PaymentType with name: "+paymentType+" is not valid.");
        }
        return paymentData;
    }
}
