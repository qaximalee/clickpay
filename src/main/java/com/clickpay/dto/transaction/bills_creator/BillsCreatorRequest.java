package com.clickpay.dto.transaction.bills_creator;

import com.clickpay.utils.enums.Months;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;


@Getter
@Setter
public class BillsCreatorRequest {

    private String month;
    private int year;
    private Long connectionType;
    private Long subLocality;
    private double amount;
    private int noOfUsers;

}
