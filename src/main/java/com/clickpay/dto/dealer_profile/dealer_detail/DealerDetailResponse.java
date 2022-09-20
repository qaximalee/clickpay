package com.clickpay.dto.dealer_profile.dealer_detail;

import com.clickpay.utils.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class DealerDetailResponse {
    @NotBlank
    private String dealerID;

    @NotBlank
    private String name;

    @NotBlank
    private String cellNo;
    private String phoneNo;

    @NotNull
    private Long companyId;
    @NotNull
    private Long localityId;
    @NotNull
    private String cnic;
    private String address;
    @NotNull
    private Date joiningDate;
    private String status;
}
