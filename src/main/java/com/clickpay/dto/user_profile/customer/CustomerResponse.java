package com.clickpay.dto.user_profile.customer;

import com.clickpay.model.user_profile.Customer;
import com.clickpay.utils.DateTimeUtil;
import com.clickpay.utils.enums.Discount;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class CustomerResponse {

    private Long id;
    private String internetId;
    private String name;
    private String connectionType;
    private Date installationDate;
    private String packageType;
    private String status;
    private String discount;
    private String address;
    private String mobile;

    public static List<CustomerResponse> mapListOfCustomerDetail(List<Object[]> data) {
        log.info("Data fetching from db is mapped to customer response.");
        return data.stream().map(e -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(Long.valueOf("" + e[0]));
            customerResponse.setInternetId(""+e[1]);
            customerResponse.setName(""+e[2]);
            customerResponse.setAddress(""+e[3]);
            customerResponse.setMobile(""+e[4]);
            customerResponse.setConnectionType(""+e[5]);
            try {
                customerResponse.setInstallationDate(DateTimeUtil.toDate(""+e[6]));
            } catch (ParseException ex) {
                log.error("ERROR: "+ex.getLocalizedMessage());
                log.error("Date parsing error occurred.");
            }
            customerResponse.setPackageType(""+e[7]);
            customerResponse.setStatus(""+e[8]);
            customerResponse.setDiscount(""+e[9]);
            return customerResponse;
        }).collect(Collectors.toList());
    }

    public static List<CustomerResponse> mapListOfCustomer(List<Customer> data) {
        log.info("Data fetching from customer entity is mapped to customer response.");
        return data.stream().map(e -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(e.getId());
            customerResponse.setInternetId(e.getInternetId());
            customerResponse.setName(e.getName());
            customerResponse.setAddress(e.getAddress());
            customerResponse.setMobile(e.getMobile());
            customerResponse.setInstallationDate(e.getInstallationDate());
            customerResponse.setStatus(e.getStatus().toString());
            customerResponse.setDiscount(e.getDiscount().toString());
            customerResponse.setConnectionType(e.getConnectionType().getType());
            customerResponse.setPackageType(e.getPackages().getPackageName());
            return customerResponse;
        }).collect(Collectors.toList());
    }
}
