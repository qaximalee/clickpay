package com.clickpay.dto.packages;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PackageRequest {

    @NotNull
    @NotBlank
    private String packageName;

    private double purchasePrice;

    @Size(min = 0, message = "Sale price should not be 0")
    private double salePrice;

    @NotNull
    @Size(min = 1)
    private Long connectionTypeId;

    @NotNull
    @Size(min = 1)
    private Long companyId;
}
