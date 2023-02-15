package com.clickpay.dto.recovery_officer.area_allocation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AreaAllocationResponse {

    private Long userId;
    private String userName;
    private String email;

    private List<AreaAllocationDto> areaAllocation;

    public static AreaAllocationResponse to(Long id, String userName, String email, List<AreaAllocationDto> areaAllocatedList) {
        return AreaAllocationResponse.builder()
                .userId(id)
                .userName(userName)
                .email(email)
                .areaAllocation(areaAllocatedList)
                .build();
    }
}
