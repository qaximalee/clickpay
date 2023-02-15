package com.clickpay.dto.recovery_officer.area_allocation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaAllocationRequest {
    private Long userId;
    private List<Long> subLocalitiesId;
}
