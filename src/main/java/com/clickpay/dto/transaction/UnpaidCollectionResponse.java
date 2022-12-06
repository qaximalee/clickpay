package com.clickpay.dto.transaction;

import com.clickpay.utils.enums.PaymentType;
import com.clickpay.utils.enums.UserCollectionStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UnpaidCollectionResponse {
    private Long id;
    private String internetId;
    private String name;
    private String connectionType;
    private List<UnpaidCollections> unpaidCollections;

}
