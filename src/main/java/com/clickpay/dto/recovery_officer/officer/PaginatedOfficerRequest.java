package com.clickpay.dto.recovery_officer.officer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedOfficerRequest {
    private String status;
    private int pageNo;
    private int pageSize;
}
