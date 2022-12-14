package com.clickpay.dto.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedUserCollectionRequest {
    private String subLocality;
    private String customerStatus;
    private String userCollectionStatus;
    private String connectionType;
    private String searchInput;
    private int pageNo;
    private int pageSize;
}
