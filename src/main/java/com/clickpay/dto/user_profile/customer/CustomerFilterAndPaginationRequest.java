package com.clickpay.dto.user_profile.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFilterAndPaginationRequest {
    private Long connectionTypeId;
    private Long packageId;
    private Long boxMediaId;
    private String status;
    private String discount;

    private int pageNo;
    private int pageSize;

}
