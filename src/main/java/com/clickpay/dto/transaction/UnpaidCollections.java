package com.clickpay.dto.transaction;

import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnpaidCollections {
    private String monthAndYear;
    private PaymentType paymentType;
    private UserCollectionStatus collectionStatus;
    private double balanceAmount;
}
