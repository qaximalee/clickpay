package com.clickpay.dto.user_profile.customer;

import com.clickpay.model.user_profile.Customer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerFilterAndPaginationResponse {
    private List<CustomerResponse> customers;

    private int pageNo;
    private int pageSize;
    private int noOfPages;
    private long totalRows;
}
