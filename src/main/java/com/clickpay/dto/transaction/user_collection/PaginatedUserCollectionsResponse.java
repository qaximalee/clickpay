package com.clickpay.dto.transaction.user_collection;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedUserCollectionsResponse {
    private List<UserCollectionResponse> userCollections;
    private int pageNo;
    private int pageSize;
    private int noOfPages;
    private long totalRows;
}
