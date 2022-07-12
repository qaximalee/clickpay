package com.clickpay.dto.user_profile.packages;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PackageRequest {

    private Long id;

    @NotBlank
    private String packageName;

    private double purchasePrice;

    @Range(min = 0, message = "Sale price should not be 0")
    private double salePrice;

    private Long connectionTypeId;
    private Long companyId;
}
