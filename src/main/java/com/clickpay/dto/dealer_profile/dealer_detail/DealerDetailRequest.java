package com.clickpay.dto.dealer_profile.dealer_detail;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealerDetailRequest extends DealerDetailResponse{
    private Long id;
}
