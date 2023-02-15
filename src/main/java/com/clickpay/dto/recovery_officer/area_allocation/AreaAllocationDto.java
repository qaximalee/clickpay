package com.clickpay.dto.recovery_officer.area_allocation;

import com.clickpay.model.area.SubLocality;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class AreaAllocationDto {

    private Long subLocalityId;
    private String subLocalityName;
    private Boolean isSelected;

    public static List<AreaAllocationDto> to(List<SubLocality> allSubLocalities, List<Long> selectedSubLocalitiesId) {
        return allSubLocalities.stream().map(e -> AreaAllocationDto.builder()
                .subLocalityId(e.getId())
                .subLocalityName(e.getName())
                .isSelected(selectedSubLocalitiesId.contains(e.getId()))
                .build())
                .collect(Collectors.toList());
    }
}
