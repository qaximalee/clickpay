package com.clickpay.utils.enums;

import com.clickpay.errors.general.BadRequestException;

import java.util.Locale;

public enum PaymentMethod {
    CASH, EASYPAISA, JAZZCASH, BANKACCOUNT;

    public static PaymentMethod of(String paymentMethod) throws BadRequestException {
        PaymentMethod paymentData = null;
        try {
            paymentData = PaymentMethod.valueOf(paymentMethod.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("PaymentMethod with name: "+paymentMethod+" is not valid.");
        }
        return paymentData;
    }
}
