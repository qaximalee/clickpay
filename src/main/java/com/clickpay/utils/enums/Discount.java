package com.clickpay.utils.enums;

import com.clickpay.errors.general.BadRequestException;

import java.util.Locale;

public enum Discount {
    HALF(1), FULL(2), QUARTER(3), SEMI(4), CUSTOM(5);

    private int value;

    Discount(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    public static Discount of(String discount) throws BadRequestException {
        Discount discountData = null;
        try {
            discountData = Discount.valueOf(discount.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Discount with name: "+discount+" is not valid.");
        }
        return discountData;
    }
}