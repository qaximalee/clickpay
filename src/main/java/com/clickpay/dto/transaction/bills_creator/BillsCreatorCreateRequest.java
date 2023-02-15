package com.clickpay.dto.transaction.bills_creator;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BillsCreatorCreateRequest {

    private String month;
    private int year;
    private Long connectionType;
    private Long subLocality;
    private double amount;
    private int noOfUsers;

}
