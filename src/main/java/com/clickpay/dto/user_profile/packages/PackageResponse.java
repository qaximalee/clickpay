package com.clickpay.dto.user_profile.packages;

import com.clickpay.dto.company.CompanyResponse;
import com.clickpay.dto.connection_type.ConnectionTypeResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageResponse {

    private String packageName;
    private double purchasePrice;
    private double salePrice;
    private ConnectionTypeResponse connectionType;
    private CompanyResponse company;
}
