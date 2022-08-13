package com.clickpay.dto.recovery_officer.officer;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OfficerRequest {

    private String name;
    private String email;
    private String address;
    private String cnic;
    private String cellNo;
    private Date joiningDate;
    private double salary = 0;
    private String userName;
    private String password;
}
