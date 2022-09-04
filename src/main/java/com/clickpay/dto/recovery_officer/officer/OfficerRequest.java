package com.clickpay.dto.recovery_officer.officer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class OfficerRequest {
    private Long id;
    @NotBlank
    private String name;

    @NotBlank
    private String email;
    private String address;

    @NotBlank
    private String cnic;

    @NotBlank
    private String cellNo;

    @NotNull
    private Date joiningDate;

    private Date leavingDate;

    private double salary = 0;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
