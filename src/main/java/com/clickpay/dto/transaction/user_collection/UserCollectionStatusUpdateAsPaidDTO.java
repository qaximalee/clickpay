package com.clickpay.dto.transaction.user_collection;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCollectionStatusUpdateAsPaidDTO {
    private Long customerId;
    private List<Long> collectionIds;
    private String paymentMethod;
    private double amount;
}
