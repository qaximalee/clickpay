package com.clickpay.dto.transaction.bills_creator;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BillsCreatorDeleteRequest {
    private Long billCreatorId;
    private String month;
    private int year;
    private Long connectionType;
    private Long subLocality;
    private int noOfUsers;
}
