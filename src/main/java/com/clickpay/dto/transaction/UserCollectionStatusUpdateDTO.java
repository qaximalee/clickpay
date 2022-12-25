package com.clickpay.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCollectionStatusUpdateDTO {
    private String status;
    private Long customerId;
    private List<Long> collectionIds;
    private String paymentMethod;
    private double amount;
}
