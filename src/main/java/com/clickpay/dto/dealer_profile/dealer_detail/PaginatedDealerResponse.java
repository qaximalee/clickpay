package com.clickpay.dto.dealer_profile.dealer_detail;

import com.clickpay.model.dealer_profile.dealer_detail.Dealer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedDealerResponse {
    private List<Dealer> dealers;

    private int pageNo;
    private int pageSize;
    private int noOfPages;
    private long totalRows;
}
