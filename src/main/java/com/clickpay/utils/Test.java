package com.clickpay.utils;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.utils.enums.Discount;

public class Test {
    public static void main(String[] args) throws BadRequestException {
        String.format("Fetching %s by id: %d", "city", 1);
        Discount discount = Discount.of("FULL");
        System.out.println("Discount: "+ discount.name()+", Value: "+discount.getValue());
    }
}
