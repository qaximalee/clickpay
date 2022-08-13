package com.clickpay.dto.user_profile.customer;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class CustomerRequest {

    private Long customerId;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String address;
    private String phone;

    @NotBlank
    private String mobile;

    @Range(min = 0, message = "installation amount should not be less than 0")
    private double installationAmount;

    @Range(min = 0, message = "installation amount should not be less than 0")
    private double otherAmount;

    @NotNull
    @DateTimeFormat
    private Date installationDate;

    @NotNull
    private Date rechargeDate;

    @NotBlank
    private String discount;

    @Range(min = 0, message = "installation amount should not be less than 0")
    private double amount;

    private Long subLocalityId;
    private Long companyId;
    private Long connectionTypeId;
    private Long boxMediaId;
    private Long packagesId;

    private boolean isCardCharge;
}
