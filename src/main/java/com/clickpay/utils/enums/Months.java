package com.clickpay.utils.enums;

import com.clickpay.errors.general.BadRequestException;

import java.util.Locale;

public enum Months {
    JAN(1), FEB(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUG(8), SEPT(9), OCT(10), NOV(11), DEC(12);

    private int value;

    Months(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    public static Months of(String month) throws BadRequestException {
        Months monthData = null;
        try {
            monthData = Months.valueOf(month.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Discount with name: "+month+" is not valid.");
        }
        return monthData;
    }
}
