package com.clickpay.dto.user_profile.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomersInUserCollectionRequest {
    private Long subLocalityId;
    private String customerStatus;
    private String userCollectionStatus;
    private Long connectionTypeId;
    private String searchInput;
}
