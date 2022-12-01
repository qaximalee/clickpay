package com.clickpay.dto.recovery_officer.officer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedOfficerResponse {
    private List<OfficerResponse> officers;
    private int pageNo;
    private int pageSize;
    private int noOfPages;
    private long totalRows;
}
