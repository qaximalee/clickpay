package com.clickpay.dto.dealer_profile.dealer_detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedDealerRequest {
    private String locality;
    private String company;
    private String status;
    private int pageNo;
    private int pageSize;
}
