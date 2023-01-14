package com.clickpay.dto.transaction.user_collection;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCollectionRequest {

    private Long id;

    @NotNull
    private Long customerId;

    @NotBlank
    private String month;

    @NotNull
    private int year;

    @Range(min = 0, message = "installation amount should not be less than 0")
    private double amount;

    @NotBlank
    private String collectionStatus;

    @NotBlank
    private String paymentType;

    private String remarks;

}
