package com.clickpay.utils;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.utils.enums.Discount;

public class Test {
    public static void main(String[] args) throws BadRequestException {
        Discount discount = Discount.of("HaLf");
        System.out.println("Discount: "+ discount.name());
    }
}
