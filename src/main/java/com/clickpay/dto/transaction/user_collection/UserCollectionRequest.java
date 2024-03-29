package com.clickpay.dto.transaction.user_collection;

import com.clickpay.model.transaction.BillsCreator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private String paymentType;

    private String remarks;

    private BillsCreator billCreator;


}
