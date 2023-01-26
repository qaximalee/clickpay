package com.clickpay.dto.transaction.bills_creator;

import com.clickpay.model.bills_creator.BillsCreator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedBillsCreatorResponse {
    private List<BillsCreator> billsCreators;
    private int pageNo;
    private int pageSize;
    private int noOfPages;
    private long totalRows;
}
