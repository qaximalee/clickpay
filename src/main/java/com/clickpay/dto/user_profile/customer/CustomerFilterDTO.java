package com.clickpay.dto.user_profile.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFilterDTO {
    private Long connectionTypeId;
    private Long packageId;
    private Long boxMediaId;
    private String status;
    private String discount;
}
